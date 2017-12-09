goog.provide('ol.style.IconImageWithProgressBar');

goog.require('ol');
goog.require('ol.dom');
goog.require('ol.events');
goog.require('ol.events.EventTarget');
goog.require('ol.events.EventType');
goog.require('ol.Image');
goog.require('ol.style');


/**
 * @constructor
 * @param {Image|HTMLCanvasElement} image Image.
 * @param {string|undefined} src Src.
 * @param {ol.Size} size Size.
 * @param {?string} crossOrigin Cross origin.
 * @param {ol.Image.State} imageState Image state.
 * @param {ol.Color} color Color.
 * @extends {ol.events.EventTarget}
 */
ol.style.IconImageWithProgressBar = function(image, src, size, crossOrigin, imageState,
                               color, repair, capacity,damageLevel) {

  ol.events.EventTarget.call(this);

  this.repair = repair;

  this.capacity = capacity;

  this.damageLevel = damageLevel;

  /**
   * @private
   * @type {Image|HTMLCanvasElement}
   */
  this.hitDetectionImage_ = null;

  /**
   * @private
   * @type {Image|HTMLCanvasElement}
   */
  this.image_ = !image ? new Image() : image;

  if (crossOrigin !== null) {
    this.image_.crossOrigin = crossOrigin;
  }

  /**
   * @private
   * @type {HTMLCanvasElement}
   */
  this.canvas_ = color ? /** @type {HTMLCanvasElement} */ (document.createElement('CANVAS')) : null;

  /**
   * @private
   * @type {ol.Color}
   */
  this.color_ = color;

  /**
   * @private
   * @type {Array.<ol.EventsKey>}
   */
  this.imageListenerKeys_ = null;

  /**
   * @private
   * @type {ol.Image.State}
   */
  this.imageState_ = imageState;

  /**
   * @private
   * @type {ol.Size}
   */
  this.size_ = size;

  /**
   * @private
   * @type {string|undefined}
   */
  this.src_ = src;

  /**
   * @private
   * @type {boolean}
   */
  this.tainting_ = false;
  if (this.imageState_ == ol.Image.State.LOADED) {
    this.determineTainting_();
  }

  this.count = 0;

};
ol.inherits(ol.style.IconImageWithProgressBar, ol.events.EventTarget);

/**
 * @param {Image|HTMLCanvasElement} image Image.
 * @param {string} src Src.
 * @param {ol.Size} size Size.
 * @param {?string} crossOrigin Cross origin.
 * @param {ol.Image.State} imageState Image state.
 * @param {ol.Color} color Color.
 * @return {ol.style.IconImage} Icon image.
 */
ol.style.IconImageWithProgressBar.get = function(image, src, size, crossOrigin, imageState,
                                   color, repair, capacity,damageLevel) {
  var iconImageCache = ol.style.iconImageCache;
  var iconImage = iconImageCache.get(src, crossOrigin, color);
  if (!iconImage) {
    iconImage = new ol.style.IconImageWithProgressBar(
        image, src, size, crossOrigin, imageState, color,repair, capacity,damageLevel);
    iconImageCache.set(src, crossOrigin, color, iconImage);
  }
  return iconImage;
};


/**
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.determineTainting_ = function() {
  var context = ol.dom.createCanvasContext2D(1, 1);
  try {
    context.drawImage(this.image_, 0, 0);
    context.getImageData(0, 0, 1, 1);
  } catch (e) {
    this.tainting_ = true;
  }
};


/**
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.dispatchChangeEvent_ = function() {
  this.dispatchEvent(ol.events.EventType.CHANGE);
};


/**
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.handleImageError_ = function() {
  this.imageState_ = ol.Image.State.ERROR;
  this.unlistenImage_();
  this.dispatchChangeEvent_();
};


/**
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.handleImageLoad_ = function() {
  this.imageState_ = ol.Image.State.LOADED;
  if (this.size_) {
    this.image_.width = this.size_[0];
    this.image_.height = this.size_[1];
  }
  this.size_ = [this.image_.width * 2, this.image_.height + 20];
  this.unlistenImage_();
  this.determineTainting_();
  this.replaceColor_();
  this.dispatchChangeEvent_();
};


/**
 * @param {number} pixelRatio Pixel ratio.
 * @return {Image|HTMLCanvasElement} Image or Canvas element.
 */
ol.style.IconImageWithProgressBar.prototype.getImage = function(pixelRatio) {
  if(this.repair>=100) {
    this.repair = -1;
  }

  if(this.capacity>=100) {
    this.capacity = -1;
  }


  var newSize = [this.size_[0], this.size_[1]];

  var context = ol.dom.createCanvasContext2D(newSize[0], newSize[1]);
  var offsetCount = 0;
  if(this.repair != -1) {
    this.drawProgressBar_({
        ctx: context,
        x: 0,
        y: offsetCount * 10,
        width: newSize[0],
        height: 8,
        color: "#006030",
        progress: this.repair,
        lineWidth: 1
    });
  }
  offsetCount++;

  if(this.capacity != -1) {
    this.drawProgressBar_({
        ctx: context,
        x: 0,
        y: offsetCount * 10,
        width: newSize[0],
        height: 8,
        color: "#C79810",
        progress: this.capacity,
        lineWidth: 1
    });
  }
  offsetCount++;

  var image = this.canvas_ ? this.canvas_ : this.image_;
  context.drawImage(image,newSize[0]/4, offsetCount * 10);

  //绘制损毁线
  this.drawDamageLevel_({
    ctx: context,
    level: this.damageLevel,
    x: newSize[0]/4,
    y: offsetCount * 10,
    width: image.width,
    height: image.height
  });

  return context.canvas;


  //context.drawImage(image,newSize[0]/4, offsetCount * 10);

  //return this.canvas_ ? this.canvas_ : this.image_;
};

ol.style.IconImageWithProgressBar.prototype.drawDamageLevel_  = function(options) {
    var ctx = options.ctx;
    var level = options.level;
    var x=options.x;
    var y=options.y;
    var width= options.width;
    var height = options.height;
    var gap = 5;
    var x1;
    var y1;
    var x2;
    var y2;
    if(level < 0) {
        return;
    }

    if(level == 0) {
        this.drawLine_(ctx, x, y, x+ width, y+ height);
        this.drawLine_(ctx, x+width, y, x, y+height);
        return;
    }

    for(var i=0; i<level; i++) {
        if(i == 0) {
            this.drawLine_(ctx, x + width, y, x, y+ height);
        } else {
            if(i % 2 != 0) {
                x1 = (x + width) - gap * (parseInt(i/2) + 1);
                y1 = y;
                x2 = x;
                y2 = (y + height) - gap * (parseInt(i/2) + 1);

            } else {
                x1 = x + width;
                y1 = y + gap * (parseInt(i/2));
                x2 = x + gap * (parseInt(i/2));
                y2 = y + height;
            }
            if(x1 < x || x1 > x+ width || x2 < x || x2 > x+width) {
                continue;
            }

            if(y1 < y || y1 > y+height || y2 < y || y2 > y + height) {
                continue;
            }
            this.drawLine_(ctx, x1, y1, x2, y2);
        }
    }
}

ol.style.IconImageWithProgressBar.prototype.drawLine_ = function(ctx, x1,y1,x2,y2) {
    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.strokeStyle="#CC0000";
    ctx.lineWidth = 2;
    ctx.stroke();
    ctx.closePath();
}

ol.style.IconImageWithProgressBar.prototype.drawProgressBar_ = function(options){
    var x = options.x;
    var y = options.y;
    var width = options.width;
    var height = options.height;
    var progress = options.progress == undefined  ? 0 : options.progress;
    var lineWidth = options.lineWidth == undefined  ? 2 : options.lineWidth;
    var ctx = options.ctx;
    var color = options.color;
    ctx.fillStyle = "#F9F7ED";
    ctx.fillRect(x, y, width, height);
    ctx.font = height + "px Courier New";
    ctx.fillStyle = color;
    ctx.fillText(progress + "%", x, y + height);

    ctx.lineWidth = lineWidth;
    ctx.strokeStyle = color;
    ctx.strokeRect(x+lineWidth+height*2+5, y+ lineWidth,width-lineWidth*2-height*2-5,height-lineWidth*2);
    ctx.fillStyle = color;
    var fillWidth = (width-lineWidth*2-height*2-5) * progress / 100;
    ctx.fillRect(x+lineWidth+height*2+5,y+ lineWidth,fillWidth,height-lineWidth*2);
};

/**
 * @return {ol.Image.State} Image state.
 */
ol.style.IconImageWithProgressBar.prototype.getImageState = function() {
  return this.imageState_;
};


/**
 * @param {number} pixelRatio Pixel ratio.
 * @return {Image|HTMLCanvasElement} Image element.
 */
ol.style.IconImageWithProgressBar.prototype.getHitDetectionImage = function(pixelRatio) {
  if (!this.hitDetectionImage_) {
    //if (this.tainting_) {
      var width = this.size_[0];
      var height = this.size_[1];
      var context = ol.dom.createCanvasContext2D(width, height);
      context.fillRect(0, 0, width, height);
      this.hitDetectionImage_ = context.canvas;
    //} else {
    //  this.hitDetectionImage_ = this.image_;
    //}
  }
  return this.hitDetectionImage_;
};


/**
 * @return {ol.Size} Image size.
 */
ol.style.IconImageWithProgressBar.prototype.getSize = function() {
  return this.size_;
};


/**
 * @return {string|undefined} Image src.
 */
ol.style.IconImageWithProgressBar.prototype.getSrc = function() {
  return this.src_;
};

ol.style.IconImageWithProgressBar.prototype.setCapacity = function(capacity){
    this.capacity = capacity;
};

ol.style.IconImageWithProgressBar.prototype.setRepair = function(repair){
    this.repair = repair;
};

ol.style.IconImageWithProgressBar.prototype.setDamageLevel = function(damageLevel){
    this.damageLevel = damageLevel;
};

/**
 * Load not yet loaded URI.
 */
ol.style.IconImageWithProgressBar.prototype.load = function() {
  if (this.imageState_ == ol.Image.State.IDLE) {
    ol.DEBUG && console.assert(this.src_ !== undefined,
        'this.src_ must not be undefined');
    ol.DEBUG && console.assert(!this.imageListenerKeys_,
        'no listener keys existing');
    this.imageState_ = ol.Image.State.LOADING;
    this.imageListenerKeys_ = [
      ol.events.listenOnce(this.image_, ol.events.EventType.ERROR,
          this.handleImageError_, this),
      ol.events.listenOnce(this.image_, ol.events.EventType.LOAD,
          this.handleImageLoad_, this)
    ];
    try {
      this.image_.src = this.src_;
    } catch (e) {
      this.handleImageError_();
    }
  }
};


/**
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.replaceColor_ = function() {
  if (this.tainting_ || this.color_ === null) {
    return;
  }

  this.canvas_.width = this.image_.width;
  this.canvas_.height = this.image_.height;

  var ctx = this.canvas_.getContext('2d');
  ctx.drawImage(this.image_, 0, 0);

  var imgData = ctx.getImageData(0, 0, this.image_.width, this.image_.height);
  var data = imgData.data;
  var r = this.color_[0] / 255.0;
  var g = this.color_[1] / 255.0;
  var b = this.color_[2] / 255.0;

  for (var i = 0, ii = data.length; i < ii; i += 4) {
    data[i] *= r;
    data[i + 1] *= g;
    data[i + 2] *= b;
  }
  ctx.putImageData(imgData, 0, 0);
};


/**
 * Discards event handlers which listen for load completion or errors.
 *
 * @private
 */
ol.style.IconImageWithProgressBar.prototype.unlistenImage_ = function() {
  this.imageListenerKeys_.forEach(ol.events.unlistenByKey);
  this.imageListenerKeys_ = null;
};


goog.provide('ol.style.IconWithProgressBar');

goog.require('ol');
goog.require('ol.asserts');
goog.require('ol.color');
goog.require('ol.events');
goog.require('ol.events.EventType');
goog.require('ol.Image');
goog.require('ol.style.IconImageWithProgressBar');
goog.require('ol.style.Image');

ol.style.IconWithProgressBar = function(opt_options) {

    var options = opt_options || {};

    /**
     * 维修进度
     *
     * @private
     * @type {number}
     */
    this.repair = options.repair !== undefined ? options.repair : -1;

    /**
     * 装载进度
     *
     * @private
     * @type {number}
     */
    this.capacity = options.capacity !== undefined ? options.capacity : -1;


    /**
     * 损伤级别
     *
     * @private
     * @type {number}
     */
    this.damageLevel = options.damageLevel !== undefined ? options.damageLevel : -1;

    /**
     * @private
     * @type {Array.<number>}
     */
    this.anchor_ = options.anchor !== undefined ? options.anchor : [0.5, 0.5];

    /**
     * @private
     * @type {Array.<number>}
     */
    this.normalizedAnchor_ = null;

    /**
     * @private
     * @type {ol.style.Icon.Origin}
     */
    this.anchorOrigin_ = options.anchorOrigin !== undefined ? options.anchorOrigin : ol.style.IconWithProgressBar.Origin.TOP_LEFT;

    /**
     * @private
     * @type {ol.style.Icon.AnchorUnits}
     */
    this.anchorXUnits_ = options.anchorXUnits !== undefined ? options.anchorXUnits : ol.style.IconWithProgressBar.AnchorUnits.FRACTION;

    /**
     * @private
     * @type {ol.style.Icon.AnchorUnits}
     */
    this.anchorYUnits_ = options.anchorYUnits !== undefined ? options.anchorYUnits : ol.style.IconWithProgressBar.AnchorUnits.FRACTION;

    /**
     * @private
     * @type {?string}
     */
    this.crossOrigin_ = options.crossOrigin !== undefined ? options.crossOrigin : null;

    /**
     * @type {Image|HTMLCanvasElement}
     */
    var image = options.img !== undefined ? options.img : null;

    /**
     * @type {ol.Size}
     */
    var imgSize = options.imgSize !== undefined ? options.imgSize : null;

    /**
     * @type {string|undefined}
     */
    var src = options.src;

    ol.asserts.assert(!(src !== undefined && image), 4); // `image` and `src` cannot be provided at the same time
    ol.asserts.assert(!image || (image && imgSize), 5); // `imgSize` must be set when `image` is provided

    if ((src === undefined || src.length === 0) && image) {
        src = image.src || ol.getUid(image).toString();
    }
    ol.asserts.assert(src !== undefined && src.length > 0, 6); // A defined and non-empty `src` or `image` must be provided

    /**
     * @type {ol.Image.State}
     */
    var imageState = options.src !== undefined ? ol.Image.State.IDLE : ol.Image.State.LOADED;

    /**
     * @private
     * @type {ol.Color}
     */
    this.color_ = options.color !== undefined ? ol.color.asArray(options.color) : null;

    /**
     * @private
     * @type {ol.style.IconImage}
     */
    this.iconImage_ = ol.style.IconImageWithProgressBar.get(image, /** @type {string} */ (src), imgSize, this.crossOrigin_, imageState, this.color_,this.repair,this.capacity,this.damageLevel);

    /**
     * @private
     * @type {Array.<number>}
     */
    this.offset_ = options.offset !== undefined ? options.offset : [0, 0];

    /**
     * @private
     * @type {ol.style.Icon.Origin}
     */
    this.offsetOrigin_ = options.offsetOrigin !== undefined ? options.offsetOrigin : ol.style.IconWithProgressBar.Origin.TOP_LEFT;

    /**
     * @private
     * @type {Array.<number>}
     */
    this.origin_ = null;

    /**
     * @private
     * @type {ol.Size}
     */
    this.size_ = options.size !== undefined ? options.size : null;

    /**
     * @type {number}
     */
    var opacity = options.opacity !== undefined ? options.opacity : 1;

    /**
     * @type {boolean}
     */
    var rotateWithView = options.rotateWithView !== undefined ? options.rotateWithView : false;

    /**
     * @type {number}
     */
    var rotation = options.rotation !== undefined ? options.rotation : 0;

    /**
     * @type {number}
     */
    var scale = options.scale !== undefined ? options.scale : 1;

    /**
     * @type {boolean}
     */
    var snapToPixel = options.snapToPixel !== undefined ? options.snapToPixel : true;

    ol.style.Image.call(this, {
        opacity: opacity,
        rotation: rotation,
        scale: scale,
        snapToPixel: snapToPixel,
        rotateWithView: rotateWithView
    });

};
ol.inherits(ol.style.IconWithProgressBar, ol.style.Image);


/**
 * Clones the style.
 * @return {ol.style.Icon} The cloned style.
 * @api
 */
ol.style.IconWithProgressBar.prototype.clone = function() {
    var oldImage = this.getImage(1);
    var newImage;
    if (this.iconImage_.getImageState() === ol.Image.State.LOADED) {
        if (oldImage.tagName.toUpperCase() === 'IMG') {
            newImage = /** @type {Image} */ (oldImage.cloneNode(true));
        } else {
            newImage = /** @type {HTMLCanvasElement} */ (document.createElement('canvas'));
            var context = newImage.getContext('2d');
            newImage.width = oldImage.width;
            newImage.height = oldImage.height;
            context.drawImage(oldImage, 0, 0);
        }
    }
    return new ol.style.IconWithProgressBar({
        anchor: this.anchor_.slice(),
        anchorOrigin: this.anchorOrigin_,
        anchorXUnits: this.anchorXUnits_,
        anchorYUnits: this.anchorYUnits_,
        crossOrigin: this.crossOrigin_,
        color: (this.color_ && this.color_.slice) ? this.color_.slice() : this.color_ || undefined,
        img: newImage ? newImage : undefined,
        imgSize: newImage ? this.iconImage_.getSize().slice() : undefined,
        src: newImage ? undefined : this.getSrc(),
        offset: this.offset_.slice(),
        offsetOrigin: this.offsetOrigin_,
        size: this.size_ !== null ? this.size_.slice() : undefined,
        opacity: this.getOpacity(),
        scale: this.getScale(),
        snapToPixel: this.getSnapToPixel(),
        rotation: this.getRotation(),
        rotateWithView: this.getRotateWithView(),
        repair: this.repair,
        capacity: this.capacity
    });
};


/**
 * @inheritDoc
 * @api
 */
ol.style.IconWithProgressBar.prototype.getAnchor = function() {
    if (this.normalizedAnchor_) {
        return this.normalizedAnchor_;
    }
    var anchor = this.anchor_;
    var size = this.getSize();
    if (this.anchorXUnits_ == ol.style.IconWithProgressBar.AnchorUnits.FRACTION ||
        this.anchorYUnits_ == ol.style.IconWithProgressBar.AnchorUnits.FRACTION) {
        if (!size) {
            return null;
        }
        anchor = this.anchor_.slice();
        if (this.anchorXUnits_ == ol.style.IconWithProgressBar.AnchorUnits.FRACTION) {
            anchor[0] *= size[0];
        }
        if (this.anchorYUnits_ == ol.style.IconWithProgressBar.AnchorUnits.FRACTION) {
            anchor[1] *= size[1];
        }
    }

    if (this.anchorOrigin_ != ol.style.IconWithProgressBar.Origin.TOP_LEFT) {
        if (!size) {
            return null;
        }
        if (anchor === this.anchor_) {
            anchor = this.anchor_.slice();
        }
        if (this.anchorOrigin_ == ol.style.IconWithProgressBar.Origin.TOP_RIGHT ||
            this.anchorOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_RIGHT) {
            anchor[0] = -anchor[0] + size[0];
        }
        if (this.anchorOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_LEFT ||
            this.anchorOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_RIGHT) {
            anchor[1] = -anchor[1] + size[1];
        }
    }
    this.normalizedAnchor_ = anchor;
    return this.normalizedAnchor_;
};


/**
 * Get the image icon.
 * @param {number} pixelRatio Pixel ratio.
 * @return {Image|HTMLCanvasElement} Image or Canvas element.
 * @api
 */
ol.style.IconWithProgressBar.prototype.getImage = function(pixelRatio) {
    return this.iconImage_.getImage(pixelRatio);
};


/**
 * Real Image size used.
 * @return {ol.Size} Size.
 */
ol.style.IconWithProgressBar.prototype.getImageSize = function() {
    return this.iconImage_.getSize();
};


/**
 * @inheritDoc
 */
ol.style.IconWithProgressBar.prototype.getHitDetectionImageSize = function() {
    return this.getImageSize();
};


/**
 * @inheritDoc
 */
ol.style.IconWithProgressBar.prototype.getImageState = function() {
    return this.iconImage_.getImageState();
};


/**
 * @inheritDoc
 */
ol.style.IconWithProgressBar.prototype.getHitDetectionImage = function(pixelRatio) {
    return this.iconImage_.getHitDetectionImage(pixelRatio);
};


/**
 * @inheritDoc
 * @api
 */
ol.style.IconWithProgressBar.prototype.getOrigin = function() {
    if (this.origin_) {
        return this.origin_;
    }
    var offset = this.offset_;

    if (this.offsetOrigin_ != ol.style.IconWithProgressBar.Origin.TOP_LEFT) {
        var size = this.getSize();
        var iconImageSize = this.iconImage_.getSize();
        if (!size || !iconImageSize) {
            return null;
        }
        offset = offset.slice();
        if (this.offsetOrigin_ == ol.style.IconWithProgressBar.Origin.TOP_RIGHT ||
            this.offsetOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_RIGHT) {
            offset[0] = iconImageSize[0] - size[0] - offset[0];
        }
        if (this.offsetOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_LEFT ||
            this.offsetOrigin_ == ol.style.IconWithProgressBar.Origin.BOTTOM_RIGHT) {
            offset[1] = iconImageSize[1] - size[1] - offset[1];
        }
    }
    this.origin_ = offset;
    return this.origin_;
};


/**
 * Get the image URL.
 * @return {string|undefined} Image src.
 * @api
 */
ol.style.IconWithProgressBar.prototype.getSrc = function() {
    return this.iconImage_.getSrc();
};


/**
 * @inheritDoc
 * @api
 */
ol.style.IconWithProgressBar.prototype.getSize = function() {
    return this.iconImage_.getSize();
};


/**
 * @inheritDoc
 */
ol.style.IconWithProgressBar.prototype.listenImageChange = function(listener, thisArg) {
    return ol.events.listen(this.iconImage_, ol.events.EventType.CHANGE,
        listener, thisArg);
};


/**
 * Load not yet loaded URI.
 * When rendering a feature with an icon style, the vector renderer will
 * automatically call this method. However, you might want to call this
 * method yourself for preloading or other purposes.
 * @api
 */
ol.style.IconWithProgressBar.prototype.load = function() {
    this.iconImage_.load();
};

ol.style.IconWithProgressBar.prototype.setCapacity = function(capacity){
    this.iconImage_.setCapacity(capacity);
};

ol.style.IconWithProgressBar.prototype.setRepair = function(repair){
    this.iconImage_.setRepair(repair);
};

ol.style.IconWithProgressBar.prototype.setDamageLevel = function(damageLevel){
    this.iconImage_.setDamageLevel(damageLevel);
};


/**
 * @inheritDoc
 */
ol.style.IconWithProgressBar.prototype.unlistenImageChange = function(listener, thisArg) {
    ol.events.unlisten(this.iconImage_, ol.events.EventType.CHANGE,
        listener, thisArg);
};


/**
 * Icon anchor units. One of 'fraction', 'pixels'.
 * @enum {string}
 */
ol.style.IconWithProgressBar.AnchorUnits = {
    FRACTION: 'fraction',
    PIXELS: 'pixels'
};


/**
 * Icon origin. One of 'bottom-left', 'bottom-right', 'top-left', 'top-right'.
 * @enum {string}
 */
ol.style.IconWithProgressBar.Origin = {
    BOTTOM_LEFT: 'bottom-left',
    BOTTOM_RIGHT: 'bottom-right',
    TOP_LEFT: 'top-left',
    TOP_RIGHT: 'top-right'
};
