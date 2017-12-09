;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-chakan" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M924.4457 918.9253l-33.2913 33.4008c-13.097 13.2055-34.3921 13.2055-47.5423 0L709.4036 817.623c-32.3 18.9276-69.8829 29.824-110.0534 29.824-118.5802 0-221.1482-101.4682-224.5601-219.9941L205.090816 627.4529l0-56.346624 175.367168 0c4.7319-20.14 12.545-38.8485 22.3406-56.3466L205.090816 514.759676l0-56.346624 241.728512 0c-0.4403 0.4403-0.6605 1.1008-1.0455 1.5411 38.8485-35.6567 90.0762-57.8867 146.8631-57.8867 120.7265 0 225.2769 104.9344 225.2769 226.046 0 41.7106-11.5558 80.6687-31.6948 113.9046l133.4927 133.932C932.864 889.1566 937.5959 905.7731 924.4457 918.9253zM592.638 469.5286c-83.5287 0-151.2663 68.012-151.2663 151.8715 0 83.8605 74.4509 158.5859 157.9796 158.5859 83.586 0 151.3216-68.0131 151.3216-151.8725C750.6729 544.2529 676.222 469.5286 592.638 469.5286zM205.0908 233.0266l394.426368 0 0 56.346624L205.090816 289.373224 205.090816 233.02656zM599.5162 358.8157c-33.5657 9.5754-65.0414 23.6616-92.884 43.2507L205.090816 402.0664l0-56.346624 394.426368 0L599.517184 358.815744zM655.8638 348.5256 655.8638 176.679936 148.743168 176.679936l0 563.46624 238.537728 0c11.9409 41.984 32.3 80.0072 59.5384 112.6922L148.743168 852.838376c-31.145 0-56.3466-25.2559-56.3466-56.3446L92.396568 120.333312c0-31.145 25.2017-56.3466 56.3466-56.3466l507.119616 0c31.0886 0 56.3456 25.2017 56.3456 56.3466l0 228.192256c-9.4095-0.8253-18.5436-2.8058-28.1743-2.8058C674.3511 345.7198 665.2723 347.7012 655.8638 348.5256z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-ceju" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M327.658552 512.784876l73.721025 73.721025 36.860513-36.860513-73.721025-73.721025L327.658552 512.784876zM401.3806 439.062827l92.151793 92.151793 36.863582-36.860513-92.154863-92.154863L401.3806 439.062827zM862.144682 383.771547l-221.167169-221.170239c-10.177803-10.177803-26.682709-10.177803-36.860513 0l-442.334337 442.334337c-10.177803 10.180873-10.177803 26.682709 0 36.860513l221.167169 221.167169c10.180873 10.177803 26.682709 10.177803 36.860513 0l442.334337-442.331267C872.323509 410.454256 872.323509 393.950374 862.144682 383.771547zM788.423657 420.633083 419.810345 789.243325c-10.177803 10.177803-26.679639 10.177803-36.860513 0L235.504712 641.797182c-10.177803-10.177803-10.177803-26.679639 0-36.860513l18.430768-18.430768 350.182544-350.182544c10.177803-10.177803 26.682709-10.177803 36.860513 0l147.446144 147.44819C798.60146 393.950374 798.60146 410.452209 788.423657 420.633083zM548.82572 291.617707l92.151793 92.154863 36.860513-36.860513-92.151793-92.154863L548.82572 291.617707zM475.101625 365.338732l73.724095 73.724095 36.860513-36.863582-73.724095-73.721025L475.101625 365.338732zM382.949832 641.797182l-92.151793-92.151793-36.863582 36.860513 92.154863 92.151793L382.949832 641.797182z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-wenzibiaozhu" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M922.126286 317.01412l-34.059724 0c-18.824741 0-34.057677-15.299451-34.057677-34.0587l-68.116377-102.177124L581.538261 180.778296 581.538261 861.954347l170.294524 68.118424c18.825764 0 34.0587 15.166421 34.0587 34.0587l0 0c0 18.759249-15.232936 34.059724-34.0587 34.059724l-204.354248 0L479.361137 998.191194 275.008936 998.191194c-18.824741 0-34.0587-15.300474-34.0587-34.059724l0 0c0-18.892279 15.232936-34.0587 34.0587-34.0587l170.294524-68.118424L445.30346 180.77932 240.950236 180.77932l-68.1174 102.177124c0 18.759249-15.233959 34.0587-34.0587 34.0587l-34.0587 0c-18.825764 0-34.059724-15.299451-34.059724-34.0587L70.655711 146.720619 70.655711 78.603219c0-18.825764 15.232936-34.059724 34.059724-34.059724l136.234801 0L479.361137 44.543496l68.116377 0 238.412948 0 136.234801 0c18.824741 0 34.0587 15.232936 34.0587 34.059724l0 68.1174 0 136.234801C956.184987 301.714669 940.951027 317.01412 922.126286 317.01412z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-tubiao04" viewBox="0 0 1707 1024">' +
    '' +
    '<path d="M1638.379128 8.027532c33.217927 0 60.2386 27.020672 60.2386 60.2386l0 750.919419c0 33.217927-27.020672 60.2386-60.2386 60.2386l-136.532263 0-8.027532 0 0 8.027532 0 117.177884-123.134312-122.861376-2.352067-2.344039-3.323398 0L68.266131 879.42415c-33.217927 0-60.2386-27.020672-60.2386-60.2386L8.027532 68.266131C8.027532 35.048204 35.048204 8.027532 68.266131 8.027532L1638.379128 8.027532M1638.379128 0 68.266131 0C30.560814 0 0 30.560814 0 68.266131l0 750.919419c0 37.705317 30.560814 68.266131 68.266131 68.266131l1296.743425 0 136.837309 136.532263 0-136.532263 136.532263 0c37.705317 0 68.266131-30.560814 68.266131-68.266131L1706.64526 68.266131C1706.637232 30.560814 1676.076418 0 1638.379128 0L1638.379128 0z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-biaozhu_icon" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M870.749152 285.952684c-119.829156-18.639522-226.373751-96.27981-347.365382-168.544671-88.382956-52.788274-210.680327-80.161714-231.962026-23.908526l-0.002047 0.00921-204.553792 811.30376 0.243547 0.061399-0.243547 0.966001c-6.049786 23.99346 8.498558 48.350195 32.492018 54.39998 23.994484 6.049786 48.348148-8.498558 54.397935-32.493041L277.302165 517.059226c264.74978-8.919137 323.505975 178.528046 413.597853 193.338356 75.022671 12.331863 199.302185-222.300992 236.372475-324.757478 10.32209-28.52466 35.971259-85.299735-56.523341-99.68742z" fill="" ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)