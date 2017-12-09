/*!
 * BSIDE v1.0.0
 * Copyright 2016-2016 thomas.chen
 * Licensed under the MIT license
 */

if (typeof jQuery === 'undefined') {
    throw new Error('BSIDE\'s JavaScript requires jQuery');
}

+function ($) {
    'use strict';

    var version = $.fn.jquery.split(' ')[0].split('.');

    if ((version[0] < 2 && version[1] < 9) || (version[0] == 1 && version[1] == 9 && version[2] < 1) || (version[0] > 3)) {
        throw new Error('BSIDE\'s JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4');
    }

}(jQuery);

/* ========================================================================
 * bside: bsside-util.js v3.3.7
 * ======================================================================== */

/**
 * 查找指定的元素在数组中的位置
 *
 * @return {number}: 返回指定元素在数组中的位置；如果没有找到，则返回-1
 */
Array.prototype.indexOf = function (o) {
  'use strict'

  for (var i = 0; i < this.length; i++) {
    if (this[i] == o) {
      return i;
    }
  }
  return -1;
}

/**
 * 删除指定的元素
 *
 * @param {anything} o : 元素
 * @return 数组自身
 */
Array.prototype.remove = function(o) {
  var index = this.indexOf(o);
  if(index > -1) {
    this.splice(index, 1);
  }
  return this;
}


/**
 * 删除数组中重复的元素
 */
Array.prototype.unique = function () {
  'use strict'

  var reset = [], done = {};
  for (var i = 0; i < this.length; i++) {
    var temp = this[i];
    if (!done[temp]) {
      done[temp] = true;
      reset.push(temp);
    }
  }
  return reset;
}

/* ========================================================================
 * bsside-treemenu.js v1.0.0
 *
 * 说明：菜单栏
 * 主要功能：
 *
 * 1. 点击叶子节点和中间节点可以有callback函数
 *
 *
 *
 *
 *
 *
 * ======================================================================== */

+function($, window, document, undefined){
  'use strict'

  var PLUGIN_NAME = 'treemenu',

      DEFAULTS = {
        data: null,                      //菜单的数据
        click: function(){return true;}  //点击链接以后，呼叫的callback
      },

      TPL = [
        '<ul class="treeview-menu"></ul>',
        '<ul class="sidebar-menu"></li>',
        '<li><a><span></span></a></li>'
      ],

      IMG_REGULAR_EXPRESS = /\.(png|jpg|gif|bmp|pic|tif)$/gi;

  function Plugin(elem, options) {
    this.elem = $(elem);
    this.setting = $.extend({}, DEFAULTS, options);
    this.load();
    this.show();
    this.event();
  }

  Plugin.prototype.load = function(){
    if (this.setting.data instanceof Array) {
      this.elem.data('cache', this.setting.data);
    }
  }

  Plugin.prototype.show = function(){
      var datas = this.elem.data('cache');
      var $ul = $(TPL[1]);
      for(var i =0;i<datas.length;i++){
        var $li = this.buildView(datas[i]);
        if($li != null){
          $ul.append($li);
        }
      }
      this.elem.append($ul);
  }

  Plugin.prototype.buildView = function (data){
    var $li = null;
    if(data == null || data == undefined){
      return $li;
    }
    var $li = $(TPL[2]);
    var $link = $li.find('a');
    var $label = $link.find('span');
    var $ul = null;

    // 处理数据中的图标
    if(data.icon){
      var isImg = IMG_REGULAR_EXPRESS.test(data.icon);
      if(isImg) {
        $link.prepend('<img src="' + data.icon + '">');
      } else {
        $link.prepend('<i class="' + data.icon + '"></i> ');
      }
    }

    if(!data.children || data.children.length == 0) {
      $link.attr('href',data.url);
    } else {
      $link.attr('href','javascript:void(0);');
      $link.append('<span class="pull-right"><i class="fa fa-angle-left"></i><span>');
      $li.addClass('treeview');
      $ul = $(TPL[0])
      $li.append($ul);
    }
    // 数据中的label
    $label.text(data.label);

    // 处理子节点数据
    for(var i=0; data.children && data.children.length > 0 && i< data.children.length; i++){
      var $ret = this.buildView(data.children[i]);
      if($ret != null) {
        $ul.append($ret);
      }
    }

    return $li;
  }

  Plugin.prototype.event = function(){
    var $elem = this.elem;
    var setting = this.setting;

    //自定义事件
    $elem.on('click', 'li', function(evt){
      var $this = $(this);
      if($this.hasClass('treeview')){
        $elem.trigger('bs.menu.node', $this);
      } else {
        $elem.trigger('bs.menu.leaf', $this);
      }
      if(!evt.isPropagationStopped()) {
        evt.stopPropagation();
      }
    });

    //点击node节点
    $elem.on('bs.menu.node', function(evt, node){
      //$elem.find('.fa').removeClass('fa-angle-down').addClass('fa-angle-left');
      $elem.find('.treeview').removeClass('active');
      var $node = $(node);
      $node.parents('.treeview').each(function(){
        $(this).addClass('active');
      });
      $node.addClass('active');
      //var child = $node.children(':first').children(':last').children(':last');
      //removeClass('fa-angle-left').addClass('fa-angle-down');
    });

    //点击叶子节点
    $elem.on('bs.menu.leaf', function(evt,leaf){

    })
  }

  $.fn[PLUGIN_NAME] = function (options) {
    return this.each(function () {
      if (!$.data(this, 'plugin_' + PLUGIN_NAME)) {
        $.data(this, 'plugin_' + PLUGIN_NAME, new Plugin(this, options));
      }
    });
  }
}(jQuery, window, document);
