var WEBGIS = WEBGIS || {};
$.ajaxSetup({ cache: false});
var G_ENV_VAR = {
    WWW: '/',
    STATIC: '/',
    BBS: '/',
    UID: '',
    UNAME: '',
    MSG_NUM: '',
    VIP: '',
    IS_CHECKED_EMAIL: false,
    IS_CHECKED_MOBILE: false,
    HAS_TRADE_PASSWORD: false,
    IS_CHECKED_IDENTIFICATION: false,
    IS_CHECKED_BANKCARD: false,
    LOADINGIMG: $("<img src='" + ctx + "/image/loading.gif' width='100px' height='20px' />"),
    QUERYERROR: $("<div>加载失败</div>"),
    keyCode: {ENTER: 13, UP: 38, DOWN: 40, TAB: 9},
    IMG_FILE_PATH: "/storage/images/"
};

/**
 * 文本信息
 * @type {Object}
 */
WEBGIS.Lang = {
    'PRODUCT_NAME': '教育质量测评系统',
    Error: {
    	'SUCCESS':'操作成功',
    	'997':'旧密码错误,修改失败',
    	'998':'数据被引用,无法删除',
        '998': '数据被引用,无法删除',
        '999': '数据异常,请刷新重试',
        '1000': '系统错误，请重试',
        '1001': '验证码错误',
        '2002': '账号或密码错误',
        //注:下面的代号的内容只是说明代号用途,具体开发时还请更换成提示信息
        '1100': '新增/修改字典明细时未填写名称',
        '1101': '新增/修改字典明细时未填写代码',
        '1102': '新增/修改字典明细时未填写显示顺序',
        '1103': '新增/修改字典明细时未选择所属字典',
        '1104': '名称重复',
        '1105': '代码重复',
        '1106': '新增/修改字典明细时显示顺序重复',

        '1130': '新增/修改所属分组时未填写名称',
        '1131': '新增/修改所属分组时未填写代码',
        '1132': '新增/修改所属分组时未填写显示顺序',
        '1133': '新增/修改所属分组时页面传入的level字段为空',
        '1134': '新增/修改所属分组时未选择父节点',
        '1135': '新增/修改所属分组时名称重复',
        '1136': '新增/修改所属分组时代码重复',
        '1137': '新增/修改所属分组时显示顺序重复',

        '1160': '新增/修改角色时未填写名称',
        '1161': '新增/修改角色时未填写显示顺序',
        '1162': '新增/修改角色时页面传入的groups字段为空',
        '1163': '新增/修改角色时名称重复',
        '1164': '新增/修改角色时显示顺序重复',

        '1190': '新增/修改用户时未填写姓名',
        '1191': '新增/修改用户时未填写用户名',
        '1192': '新增/修改用户时未填写密码',
        '1193': '新增/修改用户时未填写性别',
        '1194': '新增/修改用户时未填写所属分组',
        '1195': '新增/修改用户时用户名重复',

        '1220': '新增/修改关键观测点时未填写观测点描述',
        '1221': '新增/修改关键观测点时未填写显示顺序',
        '1222': '新增/修改关键观测点时未选中学段',
        '1223': '新增/修改关键观测点时未选中叶子节点',
        '1224': '新增/修改关键观测点时显示顺序重复',
        '1225': '新增/修改评价指标节点时名称重复',
        '1226': '新增/修改评价指标节点时代码重复',
        '1227': '新增/修改评价指标节点时显示顺序重复',

        '1300': '新增/修改试卷时未填写试卷名称',
        '1301': '新增/修改试卷时未填写试卷编号',
        '1302': '新增/修改试卷时未选择试题',
        '1303': '新增/修改试卷时试卷名称重复',
        '1304': '新增/修改试卷时试卷编号重复'
    }
};

/**
 * 浏览器版本
 * @type {{isIE6: (*|boolean), isIE7: (*|boolean)}}
 */
WEBGIS.Browser = {
    isIE6: iev && iev == 6,
    isIE7: iev && iev == 7,
    lessIE7: iev && (iev <= 7 && iev != 0),
    lessIE8: iev && (iev <= 8 && iev != 0)
};

/**
 * 辅助方法
 * @type {Object}
 */
WEBGIS.Helper = {
    /**
     * 时间戳格式化函数
     * @ref    http://ht19820316.blog.163.com/blog/static/339552332012423454937/
     * @param  {int}    timestamp 要格式化的时间戳
     * @param  {string} format    格式
     * @return {string}           格式化的时间字符串
     */
    date: function (timestamp, format) {
        var _date, _year, _month, _day, _hour, _minute, _second;
        //_date = new Date(timestamp * 1000);//时间戳要乘1000
        _date = new Date(timestamp);//时间戳要乘1000
        _year = _date.getFullYear();
        _month = (_date.getMonth() + 1 < 10) ? ('0' + (_date.getMonth() + 1)) : (_date.getMonth() + 1);
        _day = (_date.getDate() < 10) ? ('0' + _date.getDate()) : (_date.getDate());
        _hour = (_date.getHours() < 10) ? ('0' + _date.getHours()) : (_date.getHours());
        _minute = (_date.getMinutes() < 10) ? ('0' + _date.getMinutes()) : (_date.getMinutes());
        _second = (_date.getSeconds() < 10) ? ('0' + _date.getSeconds()) : (_date.getSeconds());
        if (format == 'Y-m-d h:m:s') {
            return (_year + '-' + _month + '-' + _day + ' ' + _hour + ':' + _minute + ':' + _second);
        } else if (format == 'Y-m-d') {
            return (_year + '-' + _month + '-' + _day);
        } else if (format == 'm-d') {
            return (_month + '-' + _day);
        } else if (format == 'm-d h:m:s') {
            return (_month + '-' + _day + ' ' + _hour + ':' + _minute + ':' + _second);
        } else if (format == 'm-d h:m') {
            return (_month + '-' + _day + ' ' + _hour + ':' + _minute);
        } else if (format == 'h:m:s') {
            return ( _hour + ':' + _minute + ':' + _second);
        } else if (format == 'Y-m-d h:m') {
            return (_year + '-' + _month + '-' + _day + ' ' + _hour + ':' + _minute);
        }
        else {
            return 0;
        }
    },
    /**
     * 时间转换成时间戳
     * @param date
     * @return {String}
     */
    timespan: function (date) {
        var new_str = date.replace(/:/g, '-');
        new_str = new_str.replace(/ /g, '-');
        var arr = new_str.split('-');

        var datum = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], arr[3] - 8, arr[4], arr[5]));
        //return datum.getTime() / 1000;
        return datum.getTime();
    },
    /**
     * 倒计时
     * @return {Boolean}
     */
    countDownCon: function (begintime, endtime, container) {
        begintime = parseInt(begintime), endtime = parseInt(endtime);
        var timer = null, func = function () {
            var _nowtime = new Date().getTime(),
                _second = null,
                isCount = true,
                enabledCount = 0;

            if (begintime - _nowtime > 0) {
                isCount = false;
                enabledCount = 1;
                _second = Math.round((begintime - _nowtime) / 1000);
            } else {
                isCount = true;
                if (enabledCount == 1) enabledCount = 2;
                _second = Math.round((endtime - _nowtime) / 1000);
            }
            if (_second < 0) {
                if (timer) {
                    clearTimeout(timer);
                }
                return false;
            }
            var _day = _second / (24 * 60 * 60)
            var _day = Math.floor(_day); 				//相差的总天数
            _second = _second - _day * 24 * 60 * 60; 	//抛去相差天数后的秒数
            var _hour = (_second / (60 * 60));
            var _hour = Math.floor(_hour); 			    //相差的小时数
            var _second = _second - _hour * 60 * 60;  	//抛去相差小时后的秒数
            var _min = _second / 60;
            var _min = Math.floor(_min); 				//相差的分钟数
            var _second = _second - _min * 60; 			//抛去相差分钟后的秒数
            var _sec = _second;
            _day = (_day + "").length == 1 ? "0" + _day : _day;
            _hour = (_hour + "").length == 1 ? "0" + _hour : _hour;
            _min = (_min + "").length == 1 ? "0" + _min : _min;
            _sec = (_sec + "").length == 1 ? "0" + _sec : _sec;

            if (isCount) {
                //container.html("剩余:"+_day + "天 " + _hour + ":" + _min + ":" + _sec);
                container.html("剩余:<em>" + _day + "</em>天<em>" + _hour + "</em>小时<em>" + _min + "</em>分<em>" + _sec + "</em>秒");
            } else {
                container.html("开始时间:" + WEBGIS.Helper.date(begintime, "m-d h:m:s"));
            }
            timer = setTimeout(func, 1000);
        }
        func();
    },
    /**
     * 倒计时
     * @return {Boolean}
     */
    countDown: function () {
        $(".endTime").each(function () {
            var _this = $(this), projectid = _this.attr("projectid");
            (function () {
                var investDisable = $("input[name=isRemainingTime]", _this).val();
                if (investDisable == "false") {
                    $(".time", _this).html("");
                    return false;
                }
                var endtime = $("input[name=publishEndTime]", _this).val(), begintime = $("input[name=publishStartTime]", _this).val();
                WEBGIS.Helper.countDownCon(begintime, endtime, $(".time", _this))
            })()
        });
    },
    /**
     * 构建url
     * @param url       页面连接
     * @param domain    域名(默认www)
     * @return {String} 拼接后的连接
     */
    buildUrl: function (url, domain) {
        var domain = domain || G_ENV_VAR.WWW;
        return domain + url;
    },
    /**
     * 构建ajax请求
     * @param action
     * @return {*}
     */
    ajaxAction: function (action) {
        return WEBGIS.Helper.buildUrl('ajax/' + action);
    },
    /**
     * 根据错误代码构建错误信息
     * @param errorCode     错误代码
     */
    buildError: function (errorCode) {
        return WEBGIS.Lang.Error[errorCode + ''];
    },
    /**
     * 获取中英文字符串长度
     * @param s
     * @return {Number}
     */
    getLength: function (s) {
        return s.replace(/[^\x00-\xff]/g, "aa").length;
    },
    /**
     * 手机号格式化
     * @param mobile
     * @return {String|XML}
     */
    formatMobile: function (mobile) {
        return mobile.replace(/\s/g, '').replace(/(\d{3})(\d{4})(?=\d)/g, "$1 $2 ")
    },

    /**
     * 数字前面补0
     * @param num   数字
     * @param n     数字需要补几个0
     * @returns {*}
     */
    pad:function(num, n){
        var len = num.toString().length;
        while(len < n) {
            num = "0" + num;
            len++;
        }
        return num;
    },
    /**
     * 格式化小数
     * @param input      输入
     * @param len        保留小数位数
     * @return {String}
     */
    formatDecimals: function (input, len) {
        if (input != '' && input != '0') {
            //小数点处理
            var len = len || 2,
                _index = input.indexOf('.'),
                _decimals = input.split('.')[1],
                _len;

            _decimals = _decimals || '';
            _len = _decimals.length;
            input = _len > len ? input.substring(0, _index + len + 1) : input;
        }
        return parseFloat(input);
    },
    /**
     * 保留2位小数 (小于1分进位)
     * @param number            数字
     * @param fractionDigits    小数位
     */
    toFixed: function (number, fractionDigits) {
        var _fractionDigits = fractionDigits || 2;
        with (Math) {
            return ceil(number * pow(10, _fractionDigits)) / pow(10, _fractionDigits);
        }
    },
    /**
     * 保留2位小数 (小于1分舍位)
     * @param number            数字
     * @param fractionDigits    小数位
     */
    toFloor: function (number, fractionDigits) {
        var _fractionDigits = fractionDigits || 2;
        with (Math) {
            return floor(number * pow(10, _fractionDigits)) / pow(10, _fractionDigits);
        }
    },
    /**
     * 保留2位小数 (四舍五入)
     * @param number            数字
     * @param fractionDigits    小数位
     */
    toRound: function (number, fractionDigits) {
        var _fractionDigits = fractionDigits || 2;
        with (Math) {
            return round(number * pow(10, _fractionDigits)) / pow(10, _fractionDigits);
        }
    },
    /**
     * 取区间段随机数
     * @param from   最小数
     * @param to     最大数
     */
    getRandom: function (from, to) {
        return Math.round(Math.random() * (to - from) + from);
    },
    getRandomInt: function () {
        return Math.round(Math.random() * ((1 << 30) - 1));
    },
    /**
     * 禁用按钮
     * @param btn
     */
    disabled: function (btn) {
        btn.attr('disabled', true).removeClass('investBtn').addClass('disableInvestBtn');
        return btn;
    },
    /**
     * 激活按钮
     * @param btn
     */
    enabled: function (btn) {
        btn.attr('disabled', false).removeClass('disableInvestBtn').addClass('investBtn');
        return btn;
    },
    /**
     * 检测密码强度
     * @ref     http://www.oschina.net/code/snippet_188148_6955
     * @param   input         输入
     * @return  {Number}     评分
     */
    checkStrength: function (input, el) {
        var score = 0,
            _el = el || 'strength',
            _strength = document.getElementById(_el);

        if (input.length >= 6) {
            if (/[a-zA-Z]+/.test(input) && /[0-9]+/.test(input) && /\W+\D+/.test(input)) {
                score = 3;
            } else if (/[a-zA-Z]+/.test(input) || /[0-9]+/.test(input) || /\W+\D+/.test(input)) {
                if (/[a-zA-Z]+/.test(input) && /[0-9]+/.test(input)) {
                    score = 2;
                } else if (/\[a-zA-Z]+/.test(input) && /\W+\D+/.test(input)) {
                    score = 2;
                } else if (/[0-9]+/.test(input) && /\W+\D+/.test(input)) {
                    score = 2;
                } else {
                    score = 1;
                }
            }
        } else {
            score = 0;
        }

        switch (score) {
            case 1:
                _strength.className = 's1';
                break;
            case 2:
                _strength.className = 's2';
                break;
            case 3:
                _strength.className = 's3';
                break;
            default:
                _strength.className = '';
        }
    },
    filterScript: function (str) {
        /*var reg="<script.*?>.*?</script>";
         return str.replace(reg ,'');*/

        return str.replace(new RegExp('<script[^>]*>([\\S\\s]*?)<\/script\\s*>'), '');

    },
    getKeyCode: function (e) {
        var evt = e || window.event;
        return evt.keyCode || evt.which || evt.charCode;
    },
    keyOnlyNumber: function (e) {
        var keyCode = this.getKeyCode(e)
        if (keyCode == 48 || keyCode == 96) {
            //if (sender.val() != "") {
            return true;
            //}
        } else {
            if (keyCode == 8 || keyCode == 9 || keyCode == 37 || keyCode == 39) {
                return true;
            } else {
                if (keyCode > 95 && keyCode < 106) {
                    return true;
                } else {
                    if (keyCode > 47 && keyCode < 58) {
                        return true;
                    }
                }
            }
        }
        return false;
    },
    onlyNumber: function (sender) {
        sender.keydown(function (e) {
            return WEBGIS.Helper.keyOnlyNumber(e);
        });
    },
    onlyDecmailNum: function (txtBoxObj, decimalLength) {
        if (txtBoxObj.size == 0) {
            return;
        }
        function getDecimalLength(s) {
            var index = s.indexOf(".");
            if (index < 0) {
                return 0;
            }
            return s.length - index - 1;
        }

        var lastValue = "";
        txtBoxObj.keydown(function (event) {
            var val = $.trim(txtBoxObj.val());
            if (getDecimalLength(val) > decimalLength) {
                txtBoxObj.val(lastValue);
                return false;
            }
            lastValue = val;
            var core = WEBGIS.Helper.getKeyCode(event);
            if (core == 190 || core == 110) {
                /*输入小数点*/
                if (lastValue == "" || lastValue.indexOf(".") > -1) {
                    return false;
                }
            } else {
                return WEBGIS.Helper.keyOnlyNumber(event);
            }

            return true;
        });
        txtBoxObj.keyup(function (event) {
            var val = $.trim(txtBoxObj.val());
            if (val != "") {
                try {
                    parseFloat(val);
                    if (getDecimalLength(val) > decimalLength) {
                        txtBoxObj.val(lastValue);
                    }
                }
                catch (e) {
                    txtBoxObj.val(lastValue);
                }
            }
            return true;
        });
    },
    scrollTo: function (c) {
        $(window).scrollTop(c.offset().top - 30);
    },
    /**
     * 重新发送按钮倒计时
     * @param btn
     */
    disableSendAgainBtn: function (btn) {
        if (btn.size() == 0)return;
        btn.prop({disabled: true}).addClass("disable");
        var si, i = 120;
        var timer = function (t) {
            if (t == 0) {
                btn.prop({disabled: false}).removeClass("disable").val(btn.data("msg"));
                clearInterval(si);
                return;
            }
            btn.val(t + " 秒后可以重发");
        }
        si = setInterval(function () {
            --i;
            timer(i);
        }, 1000);
    },
    hiddenScroll: function () {
        $("body").css({"padding-right": "15px", "overflow": "hidden"});
        $(".topNavMain").css({"padding-right": "15px", "overflow": "hidden"});
    },
    visibleScroll: function () {
        $("body").css({"padding-right": "0px", "overflow": "auto"});
        $(".topNavMain").css({"padding-right": "0px", "overflow": "hidden"});
    },
    /**
     * 验证是否未IE浏览器
     * @returns {boolean}
     */
    isIE: function () {
        return document.all ? true : false;
    },
    /**
     * IE文本输入框内的文字提示
     * @param fieldsObj
     */
    iePlaceholder: function (fieldsObj) {
        if (WEBGIS.Helper.isIE()) {
            var txtItems = fieldsObj != undefined ? fieldsObj : $("[placeholder]");
            txtItems.each(function () {
                var inputs = $(this);
                var placeText = inputs.attr("placeholder");
                inputs.attr("placeholder", "")
                var inputText = $.trim(inputs.val());
                if (inputText.length < 1 || inputText.toLowerCase() === placeText.toLowerCase()) {
                    inputs.addClass("txtTips").val(placeText);
                }
                inputs.focus(function () {
                    var _e = $(this);
                    inputs.attr("placeholder", "")
                    var _inputText = $.trim(_e.val());
                    if (_inputText.length < 1 || _inputText.toLowerCase() === placeText.toLowerCase()) {
                        _e.removeClass("txtTips").val("");
                    }
                }).blur(function () {
                    var _e = $(this);
                    var _inputText = $.trim(_e.val());
                    if (_inputText.length < 1 || _inputText.toLowerCase() === placeText.toLowerCase()) {
                        _e.addClass("txtTips").val(placeText);
                    }
                    inputs.attr("placeholder", placeText)
                });
            });
        }
    },
    /**
     * 回到顶部
     */
    scrollTop: function (goTop) {
        var goTop = goTop || $("#GoTop");
        var scrollTop = $(window).scrollTop(), winH = $(window).height();
        if (scrollTop > 0) {
            goTop.show();
        }
        if (WEBGIS.Browser.isIE6) {
            goTop.css({top: scrollTop + winH});
        }
        var bindEven = function () {
            goTop.click(function (e) {
                e.preventDefault();
                $("html,body").animate({scrollTop: 0}, 300);
            });
        }, bindScroll = function () {
            scrollTop = $(window).scrollTop();
            if (scrollTop > 0) {
                goTop.show();
            } else {
                goTop.hide();
            }
            if (WEBGIS.Browser.isIE6) {
                goTop.css({top: scrollTop + winH})
            }
        };
        bindEven();
        $(window).bind("scroll", bindScroll);
    },
    setCookie: function (c_name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays)
        document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
    },
    getCookie: function (c_name) {
        if (document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=");
            if (c_start != -1) {
                c_start = c_start + c_name.length + 1;
                c_end = document.cookie.indexOf(";", c_start);
                if (c_end == -1) c_end = document.cookie.length;
                return unescape(document.cookie.substring(c_start, c_end))
            }
        }
        return ""
    },
    /**
     * 添加到收藏夹
     */
    addFav: function () {
        if (document.all) {
            window.external.addFavorite('', '教育质量测评系统');
        } else if (window.sidebar) {
            window.sidebar.addPanel('教育质量测评系统', '', "");
        }
    },
    /**
     * 设置首页
     */
    setHoWEBGISage: function () {
        if (document.all) {
            document.body.style.behavior = 'url(#default#homepage)';
            document.body.setHomePage('');
        } else if (window.sidebar) {
            if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (e) {
                }
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage', '');
        }
    },
    getAgeByIdCard: function (input) {
        var idNum = input, len = idNum.length, re;
        //身份证位数检验
        if (len != 15 && len != 18) {
            return 0;
        } else if (len == 15) {
            re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
        } else {
            re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/);
        }
        //出生日期正确性检验
        var a = idNum.match(re), age = 0;
        if (a != null) {
            ''
            var now = (new Date()).getYear();
            if (len == 15) {
                var DD = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
                //var flag = DD.getYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
                age = now - DD.getYear();
            }
            else if (len == 18) {
                var DD = new Date(a[3] + "/" + a[4] + "/" + a[5]);
                //var flag = DD.getFullYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
                age = now - DD.getYear();
            }
        }
        return age;
    }
}


/**
 * 验证
 * @type {Object}
 * @ref http://hi.baidu.com/wangdawei2010/item/78921908ccd52b30f3eafcb1
 */
WEBGIS.Validator = {
    checkUserName: function (input) {
        return /^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,26}$/.test(input);
    },
    /**
     * 验证密码
     * @param input         输入
     * @return {Boolean}    验证结果
     */
    checkPassword: function (input) {
        return /^[A-Za-z0-9]{6,24}$/.test(input);
    },
    /**
     * 验证是否正整数
     * @param input         输入
     * @return {Boolean}    验证结果
     */
    checkInteger: function (input) {
        return /^\d+$/.test(input);
    },
    /**
     * 验证邮箱地址
     * @param input         输入
     * @return {Boolean}    验证结果
     */
    checkEmail: function (input) {
        return /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(input);
    },
    /**
     * 验证手机号码
     * @param input         输入
     * @return {Boolean}    验证结果
     */
    checkMobile: function (input) {
        return /^0{0,1}(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/.test(input);
    },
    /**
     * 身份证验证
     * @param input
     * @return {*}
     */
    checkIDCard: function (input) {
        var idNum = input,
            errors = new Array(
                "验证通过",
                "身份证号码位数不对",
                "身份证含有非法字符",
                "身份证号码校验错误",
                "身份证地区非法"
            ),
            re, //身份号码位数及格式检验
            len = idNum.length,
            idcard_array = new Array();

        //身份证位数检验
        if (len != 15 && len != 18) {
            return errors[1];
        } else if (len == 15) {
            re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
        } else {
            re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/);
        }
        var area = {
            11: "北京", 12: "天津", 13: "河北", 14: "山西",
            15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海",
            32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西",
            37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东",
            45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州",
            53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海",
            64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门",
            91: "国外"
        }

        idcard_array = idNum.split("");
        //地区检验
        if (area[parseInt(idNum.substr(0, 2))] == null) {
            return errors[4];
        }
        //出生日期正确性检验
        var a = idNum.match(re);
        if (a != null) {
            if (len == 15) {
                var DD = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
                var flag = DD.getYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
            }
            else if (len == 18) {
                var DD = new Date(a[3] + "/" + a[4] + "/" + a[5]);
                var flag = DD.getFullYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
            }
            if (!flag) {
                //return false;
                return "身份证出生日期不对！";
            }
            //检验校验位
            if (len == 18) {
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
                + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
                + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
                + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
                + parseInt(idcard_array[7]) * 1
                + parseInt(idcard_array[8]) * 6
                + parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1); //判断校验位
                //检测ID的校验位
                if (M == idcard_array[17]) {
                    return "1";
                }
                else {
                    //return false;
                    return errors[3];
                }
            }
        } else {
            //return false;
            return errors[2];
        }
        return true;
    },
    /**
     * 金额验证
     * @param s
     * @returns {boolean}
     */
    checkParseFloat: function (input) {
        /*-- 验证包含小数位的金额（允许输入整数、一位小数以及两位小数）--*/
        return /^((\d{1,}\.\d{2,2})|(\d{1,}\.\d{1,1})|(\d{1,}))$/.test(input);
    }
}

/**
 * AA通用api
 * @type {Object}
 */
WEBGIS.Api = {
    /**
     * 异步请求通用接口
     * @param data  自定义数据
     */
    async: function (d) {
        var _url = d.url || G_ENV_VAR.WWW,
            _type = d.type || 'POST',
            _data = d.data || '',
            _cache = d.cache || false,
            _dataType = d.dataType || 'json',
            _success = d.success,
            _error = d.error;

        $.ajax({
            url: WEBGIS.Helper.buildUrl(_url),
            type: _type,
            data: _data,
            cache: _cache,
            dataType: _dataType,
            success: function (result) {
                _success && _success(result);
            },
            error: function () {
                _error && _error();
            }
        });
    },
    /**
     * 表单提交
     * @param form      登录表单(id|jquery对象)
     * @param success
     * @param error
     */
    formSubmit: function (form, success, error) {
        var _form = $(form);
        $.ajax({
            url: _form.attr('action'),
            type: _form.attr('method'),
            data: _form.serialize(),
            dataType: 'json',
            success: function (result) {
                success && success(result);
            },
            error: function () {
                error && error();
            }
        });
    },
    /**
     * 用户
     */
    User: {
        /**
         * 获取验证码
         * 可以有两个参数：第一个是img对象，第二个是btn对象
         * @returns {string}
         */
        captcha: function () {
            var len = arguments.length;
            if (len == 1) {
                var obj = arguments[0];
                obj.attr("src", ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt());
                obj.click(function () {
                    obj.attr("src", ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt());
                });
            } else if (len == 2) {
                var obj = arguments[0];
                obj.attr("src", ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt());
                obj.click(function () {
                    obj.attr("src", ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt());
                });
                arguments[1].click(function () {
                    obj.attr("src", ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt());
                });
            } else {
                return ctx + "/kaptcha.do?" + WEBGIS.Helper.getRandomInt();
            }
        },

        /**
         * 修改密码
         */
        updatePwd: function () {
            $("#updatePwdDiv").load(ctx + "/admin/dictDetailInfo.do?dictionaryId=1&id=1");
            $.blockUI({
                message: $("#updatePwdDiv"),
                css: {
                    top: ($(window).height() - 240) / 2 + 'px',
                    left: ($(window).width() - 600) / 2 + 'px',
                    width: '600px',
                    height: '240px',
                    display: 'block'
                },
                overlayCSS: {opacity: 0}
            });
        },
        /**
         * 账户登录
         * @param form      登录表单(id|jquery对象)
         * @param success
         * @param error
         */
        login: function (form, success, error) {
            var _form = $(form);
            $.ajax({
                url: _form.attr('action'),
                type: _form.attr('method'),
                data: _form.toJSON(),
                //data:JSON.stringify(_form.serializeArray()),
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                success: function (result) {
                    success && success(result);
                },
                error: function () {
                    error && error();
                }
            });
        },
        /**
         * 账户注册
         * @param form
         * @param success
         * @param error
         */
        register: function (form, success, error) {
            var _form = $(form);
            $.ajax({
                url: _form.attr('action'),
                type: _form.attr('method'),
                data: _form.serialize(),
                dataType: 'json',
                success: function (result) {
                    success && success(result);
                },
                error: function () {
                    error && error();
                }
            });
        },
        /**
         * 用户名是否注册过
         */
        isUserNameRegistered: function (option) {
            var isTrue = false;
            var option = $.extend({
                async: true,
                username: "",
                yes: null,
                no: null
            }, option || {});
            $.ajax({
                url: ctx + "/isUserNameRegistered.do",
                data: {username: option.username},
                type: "post",
                dataType: "json",
                cache: false,
                async: option.async,
                success: function (e) {
                    switch (e.errCode) {
                        case 0:
                            isTrue = e.isRegistered == "true";
                            if (isTrue) {
                                $.isFunction(option.yes) && option.yes();
                            } else {
                                $.isFunction(option.no) && option.no();
                            }
                            break;
                        default:
                            isTrue = false;
                            $.isFunction(option.no) && option.no();
                            break;
                    }
                }
            });
            return isTrue;
        },
        /**
         * 邮箱是否注册过
         */
        isEmailRegistered: function (option) {
            var isTrue = false;
            var option = $.extend({
                async: true,
                email: "",
                yes: null,
                no: null
            }, option || {});
            $.ajax({
                url: ctx + "/isEmailRegistered.do",
                data: {email: option.email},
                type: "post",
                dataType: "json",
                cache: false,
                async: option.async,
                success: function (e) {
                    switch (e.errCode) {
                        case 0:
                            isTrue = e.isRegistered == "true";
                            if (isTrue) {
                                $.isFunction(option.yes) && option.yes();
                            } else {
                                $.isFunction(option.no) && option.no();
                            }
                            break;
                        default:
                            isTrue = false;
                            $.isFunction(option.no) && option.no();
                            break;
                    }
                }
            });
            return isTrue;
        },
        /**
         * 手机是否注册过
         */
        isMobileNoRegistered: function (option) {
            var isTrue = false;
            var option = $.extend({
                async: true,
                mobile: "",
                yes: null,
                no: null
            }, option || {});
            $.ajax({
                url: ctx + "/isMobileNoRegistered.do",
                data: {mobile: option.mobile},
                type: "post",
                dataType: "json",
                cache: false,
                async: option.async,
                success: function (e) {
                    switch (e.errCode) {
                        case 0:
                            isTrue = e.isRegistered == "true";
                            if (isTrue) {
                                $.isFunction(option.yes) && option.yes();
                            } else {
                                $.isFunction(option.no) && option.no();
                            }
                            break;
                        default:
                            isTrue = false;
                            $.isFunction(option.no) && option.no();
                            break;
                    }
                }
            });
            return isTrue;
        },
        /**
         *是否实名认证
         */
        isIdentityApprove: function (option) {
            var option = $.extend({
                yes: null,
                no: null
            }, option || {});
            var isApprove = false;
            $.ajax({
                url: ctx + "/isIdentityApprove.do",
                type: "post",
                dataType: "json",
                cache: false,
                async: false,
                success: function (json) {
                    if (json.isIdentityApprove) {
                        isApprove = true;
                        $.isFunction(option.yes) && option.yes();
                    } else {
                        $.isFunction(option.no) && option.no();
                        isApprove = false;
                    }
                },
                error: function () {
                    alert("验证出错")
                }
            });
            return isApprove;
        },
        /**
         * 是否已经登录
         */
        isLogin: function (option) {
            var option = $.extend({
                hasLogin: null,
                notLogin: null
            }, option || {});
            var isLogin = false;
            $.ajax({
                url: ctx + "/ajaxIsLogin.do",
                type: "post",
                dataType: "json",
                cache: false,
                async: false,
                success: function (json) {
                    switch (json.errCode) {
                        case 0:
                            isLogin = true;
                            WEBGIS.Api.User.showLoginedInfo(json);
                            $.isFunction(option.hasLogin) && option.hasLogin();
                            break;
                        case 1343:
                            isLogin = false;
                            $.isFunction(option.notLogin) && option.notLogin();
                            break;
                    }
                },
                error: function () {
                    isLogin = false;
                }
            });
            return isLogin;
        },
        showLoginedInfo: function (json) {
            $(".notLogin").hide();
            $(".logined").show();
            $(".spanUserName").html(json.userName);
            $("input[name=hidCookieUsername]").val(json.userName);
            $("#vipSpan").removeAttr("class");
            $("#vipSpan").addClass("customerVip vip" + json.vipLevel);
            var msgSize = json.msgSize;
            if (msgSize != undefined) {
                var newsCount = $(".newsCount")
                newsCount.html(msgSize);
                newsCount.show();
                var _msgBox = $(".newsCount").parent().siblings(".navDown");
                $(".t", _msgBox).html(json.msgTitle);
                $(".d", _msgBox).html(json.msgTime);
                $(".content", _msgBox).html(json.msgContent);
                _msgBox.removeClass("navDownHide");
            }

        }
    }
};

/**
 * AA视图初始化
 * @type {Object}
 */
WEBGIS.View = {
    /**
     * 通用弹出层信息
     */
    PopupWin: {
        showMessage: function (option) {
            option = $.extend({
                iconCss: "icon_hint",//icon_error,icon_warning,icon_finish,icon_hint,icon_query,icon_warningSmall
                title: "信息提示",
                content: "",
                callback: ""
            }, option || {});

            var _content = "<div class=\"m-normalPop s-bsdStyle1\"><div class=\"tt\"><strong>" + option.title + "</strong></div>" +
                "<div class=\"ct editCon\"><div class=\"customTipsWrap width344\"><i class=\"" + option.iconCss + " floatL\"></i>" +
                "<div class=\"floatL font14 paddingT3\" style='width:200px;'>" + option.content + "</div><div class=\"clear\"></div></div></div>" +
                "<div class=\"ft\"><div class=\"option\"><input id=\"cBtn\" type=\"button\" class=\"customBtn colourLightGray\" value=\"关闭\">" +
                "</div></div></div><script type=\"text/javascript\">$(\"#cBtn\").click(function(){$.unblockUI();});";
            if (option.callback != null && option.callback != "") {
                _content = _content + "$(\"#cBtn\").click(" + option.callback + ");";
            }
            _content = _content + "</script>";

            $.blockUI({
                message: _content,
                css: {
                    top: ($(window).height() - 200) / 2 + 'px',
                    left: ($(window).width() - 300) / 2 + 'px',
                    width: '300px',
                    height: '160px',
                    display: 'block'
                },
                overlayCSS: {opacity: 0}
            });
        },
        showConfirmMessage: function (option) {
            option = $.extend({
                iconCss: "icon_hint",//icon_error,icon_warning,icon_finish,icon_hint,icon_query,icon_warningSmall
                title: "信息提示",
                content: "",
                OkFunc: "",
                CancelFunc: ""
            }, option || {});

            var _content = "<div class=\"m-normalPop s-bsdStyle1\"><div class=\"tt\"><strong>" + option.title + "</strong></div>" +
            "<div class=\"ct editCon\"><div class=\"customTipsWrap width344\"><i class=\"" + option.iconCss + " floatL\"></i>" +
            "<div class=\"floatL width298 font14 paddingT3\">" + option.content + "</div><div class=\"clear\"></div></div></div>" +
            "<div class=\"ft\"><div class=\"option\"><input id=\"sBtn\" type=\"submit\" onclick="+option.OkFunc+" class=\"customBtn colourDarkBlue\" value=\"确认\">" +
            "&nbsp;&nbsp;<input id=\"cBtn\" type=\"button\" onclick="+option.CancelFunc+" class=\"customBtn colourLightGray\" value=\"取消\">" +
            "</div></div></div>";
            $.blockUI({
                message: _content,
                css: {
                    top: ($(window).height() - 200) / 2 + 'px',
                    left: ($(window).width() - 300) / 2 + 'px',
                    width: '300px',
                    height: '160px',
                    display: 'block'
                },
                overlayCSS: {opacity: 0}
            });
        },
        showMessageWithCallback: function (option,cb) {
            option = $.extend({
                iconCss: "icon_hint",//icon_error,icon_warning,icon_finish,icon_hint,icon_query,icon_warningSmall
                title: "信息提示",
                content: "",
                callback: cb
            }, option || {});

            var _content = "<div class=\"m-normalPop s-bsdStyle1\"><div class=\"tt\"><strong>" + option.title + "</strong></div>" +
                "<div class=\"ct editCon\"><div class=\"customTipsWrap width344\"><i class=\"" + option.iconCss + " floatL\"></i>" +
                "<div class=\"floatL font14 paddingT3\" style='width:200px;'>" + option.content + "</div><div class=\"clear\"></div></div></div>" +
                "<div class=\"ft\"><div class=\"option\"><input id=\"cBtn\" type=\"button\" class=\"customBtn colourLightGray\" value=\"关闭\">" +
                "</div></div></div><script type=\"text/javascript\">$(\"#cBtn\").click(function(){$.unblockUI();});";//$(\"#inputTmp\").focus();
            if (option.callback != null && option.callback != "") {
                _content = _content + "$(\"#cBtn\").click(" + option.callback + ");";
            }
            _content = _content + "</script>";

            $.blockUI({
                message: _content,
                css: {
                    top: ($(window).height() - 200) / 2 + 'px',
                    left: ($(window).width() - 300) / 2 + 'px',
                    width: '300px',
                    height: '160px',
                    display: 'block'
                },
                overlayCSS: {opacity: 0}
            });
        }
    },
    /**
     * 通用吊顶
     */
    TopNav: {
        /**
         * 初始化
         */
        init: function () {

        }
    },
    /**
     * 浮动层
     */
    Box: {
        /**
         * 显示
         * @param option(object)
         */
        show: function (option) {
            option = $.extend({
                url: "",
                content: "",
                title: "",
                before: null,
                success: null,
                error: null,
                cssClass: null,
                css: null,
                overflow: false,
                imgArry: null,
                imgIndex: 0,
                blurClose: false,
                data: {}
            }, option || {});

            try {
                var _box;
                if ($(".floatBox").size() > 0) {
                    _box = $(".floatBox");
                } else {
                    _box = this.create(option.title);
                }
                if (option.overflow && !WEBGIS.Browser.isIE7 && !WEBGIS.Browser.isIE6) {
                    WEBGIS.Helper.hiddenScroll();
                } else {
                    WEBGIS.Helper.visibleScroll();
                }
                $(".boxMask").css({"background": "url('/assets/img/common/ajaxLoad2.png') no-repeat center center #000000"});
                WEBGIS.View.Box.hackIE6();
                if (option.cssClass != null) {
                    _box.addClass(option.cssClass);
                }
                if (option.css != null) {
                    _box.css(option.css);
                }
                //关闭按钮事件
                $(".close", _box).bind("click", this.remove);
                if (option.blurClose) {
                    $(".boxMask").bind("click", this.remove);
                }
                if (option.imgArry && option.imgArry.length > 0) {
                    WEBGIS.View.Box.loadImgs(option.imgArry, option.imgIndex, _box);
                    return;
                }
                if (option.url != "") {
                    var urlString = /\.jpg$|\.jpeg$|\.png$|\.gif$|\.bmp$/;
                    var urlType = option.url.toLowerCase().match(urlString);

                    if (urlType == ".jpg" || urlType == ".jpeg" || urlType == ".png" || urlType == ".gif" || urlType == ".bmp") {
                        WEBGIS.View.Box.loadImg(option.url, _box);
                    } else {
                        //load页面
                        $.ajax({
                            type: "post",
                            url: option.url,
                            data: option.data,
                            dataType: "html",
                            cache: false,
                            timeout: 60000,
                            success: function (data) {
                                _box.find(".boxContent").html(data);
                                $(".boxMask").css({"background": "#000000"});
                                WEBGIS.View.Box.position();
                                _box.fadeIn();
                                $.isFunction(option.success) && option.success();
                            }, error: function () {
                                $.isFunction(option.error) && option.error();
                            }
                        });
                    }
                } else {
                    _box.find(".boxContent").html(option.content);
                    $(".boxMask").css({"background": "#000000"});
                    WEBGIS.View.Box.position();
                    _box.show();
                    $.isFunction(option.success) && option.success();
                }
            }
            catch (err) {
                if ($.isFunction(option.error)) {
                    option.error();
                }
            }
        },
        create: function (title) {
            var _box = $("<div class='floatBox'><div class='box'><div class='boxTitle'><div class='text'>" + title + "</div><a class='close'></a></div><div class='boxContent'></div></div></div>");
            $("body").append(_box);
            $("body").append("<div class='boxMask'></div>");
            return _box;
        },
        loadImgs: function (arry, index, _box) {
            var picBox = $("<div  class='prev' ></div><div class='pic'></div><div  class='next' ></div>");
            _box.find(".boxContent").html(picBox);

            var count = arry.length, pic = $(".pic", _box), prevBtn = $(".prev", _box), nextBtn = $(".next", _box);
            var auto = function () {
                if (index == 0) {
                    prevBtn.hide();
                    nextBtn.show();
                } else if (index > 0 && index < count - 1) {
                    prevBtn.show();
                    nextBtn.show();
                } else if (index == count - 1) {
                    prevBtn.show();
                    nextBtn.hide();
                }
                var _url = arry[index];
                var img = new Image();
                img.src = _url;
                //pic.html(img);
                img.onclick = function () {
                    WEBGIS.View.Box.remove();
                };
                $(".boxMask").click(function () {
                    loadTimer && clearInterval(loadTimer);
                    WEBGIS.View.Box.remove();
                });
                var winW = $(window).width(), winH = $(window).height();
                var complete = function () {
                    loadTimer && clearInterval(loadTimer);
                    pic.html(img);
                    $(".boxMask").css({"background": "#000000"});
                    $(".loading", pic).remove();

                    var imgH = img.height, imgW = img.width;
                    if (imgH > winH) {
                        img.height = winH - 30;
                    }
                    var d = (imgH - img.height) / imgH;
                    img.width = imgW - imgW * d;

                    var imgW = img.width || $("img", pic).width();
                    _box.css({width: imgW + 20});
                    WEBGIS.View.Box.position();
                    _box.fadeIn();
                };
                if (img.complete) {
                    complete();
                } else {
                    pic.append("<div class='loading'></div>");
                    var loadTimer = setInterval(function () {
                        if (img.complete) {
                            complete();
                        }
                    }, 200);
                }
            }, prev = function () {
                index--;
                if (index < 0) index = 0;
                auto();
            }, next = function () {
                index++;
                if (index >= count) index = count - 1;
                auto();
            };
            auto();
            prevBtn.click(prev);
            nextBtn.click(next);
        },
        loadImg: function (_url, _box) {
            var img = new Image();
            img.src = _url;
            _box.find(".boxContent").html(img);
            img.onclick = function () {
                WEBGIS.View.Box.remove();
            };
            $(".boxMask").click(function () {
                loadTimer && clearInterval(loadTimer);
                WEBGIS.View.Box.remove();
            });
            var winW = $(window).width(), winH = $(window).height();
            var complete = function () {
                loadTimer && clearInterval(loadTimer);
                $(".boxMask").css({"background": "#000000"});

                var imgH = img.height, imgW = img.width;
                if (imgH > winH) {
                    img.height = winH - 30;
                }
                var d = (imgH - img.height) / imgH;
                img.width = imgW - imgW * d;

                _box.css({width: img.width + 20});
                WEBGIS.View.Box.position();
                _box.fadeIn();
            };
            if (img.complete) {
                complete();
            } else {
                var loadTimer = setInterval(function () {
                    if (img.complete) {
                        complete();
                    }
                }, 200);
            }
        },
        top: function () {
            $(".floatBox").css({"top": $(window).scrollTop() + $(window).height() / 2});
        },
        position: function () {
            var _box = $(".floatBox"), w = _box.outerWidth(), h = _box.outerHeight(), ml = -(w / 2), mt = -(h / 2);
            _box.css({"margin-left": ml, "margin-top": mt});
            if (WEBGIS.Browser.isIE6) {
                WEBGIS.View.Box.top();
            }
        },
        remove: function () {
            WEBGIS.Helper.visibleScroll();
            $(".floatBox").remove();
            $(".boxMask").remove();
        },
        hackIE6: function () {
            if (WEBGIS.Browser.isIE6) {
                $(".boxMask").css({
                    "position": "absolute",
                    "width": $("body").outerWidth(),
                    "height": $("body").outerHeight()
                });
                $(window).scroll(function () {
                    WEBGIS.View.Box.top();
                });
            }
            ;
        }
    },
    /**
     *tab menu事件，init的参数顺序是tab的顺序
     */
    TabMenu: {
        bind: function () {
            var args = arguments, hasArgument = arguments.length > 0, menus = $("a", $(".tabMenu")), contents = $(".tabContent");
            menus.each(function (index) {
                var _obj = $(this);
                _obj.click(function () {
                    menus.removeClass("active");
                    _obj.addClass("active");
                    hasArgument && args[index]();
                    if (contents.size() > 0) {
                        contents.hide();
                        contents.eq(index).show();
                    }
                });
            });
            this.initChoose();
        },
        show: function (index) {
            var menus = $("a", $(".tabMenu"));
            if (index >= menus.length) return;
            menus.eq(index).click();

        },
        initChoose: function () {
            var hash = location.hash;
            if (hash != "") {
                $("[dataid=" + hash + "]").click();
            } else {
                WEBGIS.View.TabMenu.show(0);
            }
        }
    },
    /**
     * 首页banner效果
     */
    Banner: {
        /**
         * 绑定banner效果
         * @param buttonHover 按钮选中样式
         * @param buttons     按钮集
         * @param imgs        banner集
         */
        bind: function (buttonHover, buttons, imgs) {
            if (imgs.size() <= 1) return false;
            var timer, index = 0, prev = 0;
            var defaultPlay = function (index) {
                buttons.removeClass(buttonHover);
                buttons.eq(index).addClass(buttonHover);
                imgs.hide();
                imgs.eq(index).show();
            }, auto = function (i) {
                buttons.removeClass(buttonHover);
                buttons.eq(i).addClass(buttonHover);

                imgs.eq(prev).fadeOut('fast', function () {
                    imgs.eq(i).fadeIn();
                });
                prev = i;
            }, play = function () {
                timer = setInterval(function () {
                    index = index + 1;
                    if (index >= imgs.size()) {
                        index = 0;
                    }
                    auto(index);
                }, 5000);
            };
            buttons.each(function () {
                var obj = $(this);
                obj.hover(function () {
                    clearInterval(timer);
                    var buttonIndex = buttons.index(obj);
                    if (buttonIndex == index) {
                        return;
                    }
                    index = buttonIndex;
                    auto(index);
                }, function () {
                    play();
                });
            });
            play();
            defaultPlay(index);
        }
    },
    /**
     * 页码ajax效果
     */
    Pager: {
        bind: function (callBack, pagers, container) {
            pagers = pagers || $("a", $(".pageBox"));
            pagers.click(function (e) {
                e.preventDefault();
                var href = $(this).attr("href");
                if (container != undefined && container.size() > 0) {
                    $.isFunction(callBack) && callBack(href, function () {
                        WEBGIS.View.Pager.bind(callBack);
                    }, container);
                } else {
                    $.isFunction(callBack) && callBack(href, function () {
                        WEBGIS.View.Pager.bind(callBack);
                    });
                }
                return false;
            });
        }
    },
    Picture: {
        imgArry: null,
        init: function (imageArry, container) {
            if (WEBGIS.View.Picture.create(imageArry, container)) {
                WEBGIS.View.Picture.bind($("ul", container), $("li", container));
            }
        },
        bind: function (container, items) {
            var arry = WEBGIS.View.Picture.imgArry;
            items.click(function () {
                var index = items.index(this), url = window.location.href;
                WEBGIS.Api.User.isLogin({
                    hasLogin: function () {
                        WEBGIS.View.Box.show({overflow: true, cssClass: "imgBox", imgArry: arry, imgIndex: index});
                    },
                    notLogin: function () {
                        window.location.href = ctx + "/login.do?returnUrl=" + encodeURI(url);
                    }
                });
            });
        },
        create: function (imageArry, container) {
            this.imgArry = new Array();
            if (imageArry && imageArry.length > 0) {
                var ul = $("ul", container);
                var i, m, s, img;
                for (var item in imageArry) {
                    i = imageArry[item];
                    m = "/storage/images/" + i.imageFileId, s = "/storage/images/" + i.thumbnailFileId;
                    this.imgArry.push(m);
                    (function () {
                        img = new Image();
                        img.src = s;
                        img.width = 160;
                        img.height = 120;
                        //var li = $("<li data-img='"+m+"'></li>");
                        var li = $("<li data-img=''></li>");
                        li.html(img);
                        ul.append(li);
                        if (img.complete) {
                            li.css({"background": "none"});
                        } else {
                            li.css({"background": "url(" + ctx + "/images/loading.gif) no-repeat center center"});
                            var timer = setInterval(function () {
                                if (img.complete) {
                                    timer && clearInterval(timer);
                                    li.css({"background": "none"});
                                }
                            }, 200);
                        }
                    })()
                }
                container.show();
                return true;
            }
            return false;
        }
    },
    Table: {
        odd: function () {
            $("tbody>tr:odd").addClass("odd");
        }
    },
    /**
     * 浮动提示层
     */
    Tip: {
        show: function (option) {
            option = $.extend({
                content: "",
                error: null,
                css: null,
                cssClass: null,
                arrowsCss: null
            }, option || {});
            var _tip = WEBGIS.View.Tip.create();
            $("body").append(_tip);
            $(".c", _tip).html(option.content);
            _tip.addClass(option.cssClass);

            if (option.arrowsCss != null) {
                _tip.find("i").css(option.arrowsCss);
            }

            WEBGIS.View.Tip.position(option.css);
            _tip.show();
        },
        create: function () {
            WEBGIS.View.Tip.remove();
            return $("<div class='tipBox'><div class='c'></div><i></i><div class='clear'></div></div>");
        },
        position: function (css) {
            var _tip = $(".tipBox");
            _tip.css(css);
            _tip.css({top: css.top - _tip.outerHeight() - 12});
        },
        remove: function () {
            $(".tipBox").remove();
        }
    }
};

(function ($) {
    jQuery.fn.extend({
        chooseAll: function (childrenChkName) {
            var sender = $(this);
            sender.click(function () {
                var isChecked = $(this).prop("checked");
                jQuery(":checkbox[name=" + childrenChkName + "]:not(:disabled)").prop({checked: isChecked});
                sender.attr({checked: isChecked});
            });
            jQuery(":checkbox[name=" + childrenChkName + "]").click(function () {
                var isChecked = $(this).prop("checked");
                var checkedCount = jQuery(":checkbox[name=" + childrenChkName + "]:checked,:checkbox[name=" + childrenChkName + "]:disabled").size();
                var totalCount = jQuery(":checkbox[name=" + childrenChkName + "]").size();
                if (checkedCount == totalCount) {
                    sender.attr({checked: true});
                } else {
                    sender.attr({checked: false});
                }
            });
        },
        getChooseVal: function () {
            var chooseCheckbox = jQuery(this).filter(":checked");
            var chkValArr = jQuery.map(chooseCheckbox, function (item) {
                return jQuery(item).val();
            });
            return chkValArr.toString();
        },
        getChooseValArry: function () {
            var chooseCheckbox = jQuery(this).filter(":checked");
            var chkValArr = jQuery.map(chooseCheckbox, function (item) {
                return jQuery(item).val();
            });
            return chkValArr;
        },
        getNotChooseVal: function () {
            var chooseCheckbox = jQuery(this).not(":checked");
            var chkValArr = jQuery.map(chooseCheckbox, function (item) {
                return jQuery(item).val();
            });
            return chkValArr.toString();
        },
        isCheck: function () {
            var thisObj = jQuery(this);
            var flag = false;
            thisObj.each(function () {
                if ($(this).attr("checked") == true) {
                    flag = true;
                    return;
                }
            });
            return flag;
        },
        disabled: function () {
            var thisObj = $(this);
            thisObj.attr({disabled: true});
            thisObj.css({opacity: "0.5", cursor: "auto"});
        },
        removeDisabled: function () {
            var thisObj = $(this);
            thisObj.removeAttr("disabled");
            thisObj.css({opacity: "1", cursor: "pointer"});
        },
        mustLoginLink: function () {
            var thisObj = $(this);
            thisObj.click(function () {
                var url = $(this).attr("href");
                WEBGIS.Api.User.isLogin({
                    hasLogin: function () {
                        window.location.href = url;
                    },
                    notLogin: function () {
                        window.location.href = ctx + "/login.do?returnUrl=" + encodeURI(url);
                    }
                });
                return false;
            });
        },
        center: function () {
            return this.each(function () {
                var top = ($(window).height() - $(this).outerHeight()) / 2;
                var left = ($(window).width() - $(this).outerWidth()) / 2;
                $(this).css({
                    position: 'absolute',
                    margin: 15,
                    top: (top > 0 ? top : 0) + 'px',
                    left: (left > 0 ? left : 0) + 'px'
                });
            });
        },
        serializeObject:function(){
        	var o = {};
        	var a = this.serializeArray();
        	$.each(a,function(){
        		if(o[this.name] !== undefined){
        			if(!o[this.name].push){
        				o[this.name] = [o[this.name]];
        			}
        			o[this.name].push(this.value || '');
        	    }else{
        	    	o[this.name] = this.value || '';
        	    }
        	});
        	return o;
        }
    });
})(jQuery);

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};
/*-- 替换 --*/
String.prototype.replaceAll = function (source, target, ignoreCase) {
    if (ignoreCase == null) {
        ignoreCase = false;
    }
    source = source.replace(/([\\.$^{[(|)*+?\\\\])/g, "\\$1");
    if (!RegExp.prototype.isPrototypeOf(source)) {
        return this.replace(new RegExp(source, (ignoreCase ? "gi" : "g")), target);
    } else {
        return this.replace(source, target);
    }
};
/*-- 字符串占位格式化 --*/
String.prototype.format = function () {
    if (arguments == null || arguments.length == 0) {
        return this;
    }
    var s = this;
    for (var i = 0, len = arguments.length; i < len; i++) {
        s = s.replaceAll("{" + i.toString() + "}", arguments[i].toString());
    }
    return s;
};
/*字符串转json,过滤一些换行符*/
String.prototype.json = function () {
    if (this == "") return "";
    return $.parseJSON(this.replace(/[\r]/g, "<br>").replace(/[\t]/g, "").replace(/[\n]/g, ""));
};
/*项目描述截取*/
String.prototype.subDes = function (length) {
    var s = this;
    if (length && length > 0 && s.length > 0) {
        return s.substring(0, length) + "......";
    }
    return s;
};
/*-- 清除空格  --*/
String.prototype.clearSpace = function () {
    return this.replace(/\s+/g, "");
};
String.prototype.escapeHTML = function () {
    return this.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
};
String.prototype.unescapeHTML = function () {
    return this.stripTags().replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
}

/*Number.prototype.toFixed = function(fractionalDigits) {
 var n = this.toFixed(fractionalDigits);
 };*/

function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 100) / 100;
    var s = new String(f);
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
};
function setMinHeight() {
    var headerNavH = $(".header").outerHeight(),
        footerH = $(".footer").outerHeight(),
        wrap = $(".wrap ").outerHeight(),
        topNavH = $(".topNav").outerHeight(),
        _h = $(window).outerHeight() - headerNavH - footerH + 5;
    if (_h > wrap) $(".wrap ").css({"min-height": _h});
};

function siteDes() {
    var site_backdrop = $("#site_backdrop"), marginTop = 0;
    var auto = function () {
        marginTop = marginTop == 0 ? -40 : 0;
        site_backdrop.animate({
            "marginTop": marginTop + "px"
        }, 500);
    }
    setInterval(function () {
        auto()
    }, 3000);
};
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}


$(function () {
    //菜单下拉
    $(".nav ul>li").hover(function () {
            $(this).find("span").css("display", "block");
            $(this).find(".secUl").css("display", "block");
        }, function () {
            $(this).find("span").css("display", "none");
            $(this).find(".secUl").css("display", "none");
        }
    );

    //按下ESC键时关闭弹出窗
    /*$(document).keydown(function (event) {
        if (event.keyCode == 27) {
            $.unblockUI();
        }
    });*/
});

/*
 * MAP对象，实现MAP功能
 *
 * 接口：
 * size()     			获取MAP元素个数
 * isEmpty()    			判断MAP是否为空
 * clear()     			删除MAP所有元素
 * put(key, value)   	向MAP中增加元素（key, value)
 * remove(key)    		删除指定KEY的元素，成功返回True，失败返回False
 * get(key)    			获取指定KEY的元素值VALUE，失败返回NULL
 * element(index)   		获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
 * containsKey(key)  	判断MAP中是否含有指定KEY的元素
 * containsValue(value) 判断MAP中是否含有指定VALUE的元素
 * values()    			获取MAP中所有VALUE的数组（ARRAY）
 * keys()     			获取MAP中所有KEY的数组（ARRAY）
 * load(url,param,key)	通过url初始化Map对象
 * 例子：
 * var map = new Map();
 *
 * map.put("key", "value");
 * var val = map.get("key")
 * ……
 * map.load("aaa.action",{userId:12345},"id");第三个参数key可选，默认为"id",加载后的数据以key为键,其对应的json对象为值
 */
function Map() {
    this.elements = {};
    this.length = 0;
    //获取MAP元素个数
    this.size = function () {
        return this.length;
    };

    //判断MAP是否为空
    this.isEmpty = function () {
        return this.length < 1;
    };

    //删除MAP所有元素
    this.clear = function () {
        this.elements = {};
        this.length = 0;
    };

    //向MAP中增加元素（key, value)
    this.put = function (_key, _value) {
        if (this.elements[_key] == null) {
            this.length += 1;
        }
        this.elements[_key] = _value;
    };

    //删除指定KEY的元素，成功返回True，失败返回False
    this.removeByKey = function (_key) {
        if (this.elements[_key] != null) {
            delete this.elements[_key];
            this.length -= 1;
        }
    };

    //获取指定KEY的元素值VALUE，失败返回NULL
    this.get = function (_key) {
        return this.elements[_key];
    };

    //判断MAP中是否含有指定KEY的元素
    this.containsKey = function (_key) {
        if (this.elements[_key] != null) {
            return true;
        }
        else {
            return false;
        }
    };

    //获取MAP中所有VALUE的数组（ARRAY）
    this.values = function () {
        var arr = new Array();
        for (var key in this.elements) {
            arr.push(this.elements[key]);
        }
        return arr;
    };

    //获取MAP中所有KEY的数组（ARRAY）
    this.keys = function () {
        var arr = new Array();
        for (var key in this.elements) {
            arr.push(key);
        }
        return arr;
    };

    //通过json对象初始化Map对象
    this.loadByJsonArray = function (jsonArray, key) {
        if (key == null)key = "id";
        this.elements = {};
        this.length = 0;
        if (jsonArray != null && jsonArray.length > 0) {
            for (var i = 0; i < jsonArray.length; i++) {
                var json = jsonArray[i];
                if (this.elements[json[key]] == null) {
                    this.length += 1;
                }
                this.elements[json[key]] = json;
            }
        }
    };
    //通过json对象初始化Map对象
    this.appendByJsonArray = function (jsonArray, key) {
        if (key == null)key = "id";
        if (jsonArray != null && jsonArray.length > 0) {
            for (var i = 0; i < jsonArray.length; i++) {
                var json = jsonArray[i];
                if (this.elements[json[key]] == null) {
                    this.length += 1;
                }
                this.elements[json[key]] = json;
            }
        }
    };
    this.clone = function () {
        var newMap = new Map();
        for (var key in this.elements) {
            newMap.put(key, this.elements[key]);
        }
        return newMap;
    };
}
