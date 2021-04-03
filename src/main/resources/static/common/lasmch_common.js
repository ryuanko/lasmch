$(document).ready(function(){
    loadingOut()
    setDatepicker();
    setSmartEditor();

    if (typeof axios != "undefined") {
      //Axios Interceptors
      axios.interceptors.request.use(function(config) {
        if (config.method === 'get') {
          if (config.params === undefined) config.params = {}
          config.params._ = (new Date().getTime())
        }
        return config
      }, function (error) {
        return err
      })

      axios.interceptors.response.use(function (response)  {
        loadingOut()
        return response
      }, function (error) {
        loadingOut()
        var error_msg = ''
        var error_callback = ''
        if(error.response.status === 404) {
          error_msg = '데이터가 존재하지 않거나 잘못된 요청입니다.'
        }
        console.log(error.response)

        if(error.response.status === 400 || error.response.status === 500) {
          error_msg = _.get(error, 'response.data.message') || 'Server Error'
        }

        console.log('error_msg: ', error_msg)

        if (!_.isEmpty(error_msg)) {
            setTimeout(function () {
              alertPopup(error_msg) // 팝업창 open
            }, 300)
        }
        return error
      })
    }

    window.onpageshow = function(event) {
        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
            loadingOut()

            if (typeof app != "undefined" && !_.isEmpty(app.params)) {
                setTimeout(function () {
                    var re_dat_setting = app.params
                    app.params = {}
                    app.params = re_dat_setting
                }, 50)
            }
        }
    }

});


function setKeyword (params, option) {
  if (!option) option = ['parentid', 'topid', 'depth', 'step']
  var query = $.map(params, function(value, key){
     if (value) {
        var cnt = _.filter(option, function(i) { return key === i }).length
        if (cnt === 0) {
          return key + '=' + encodeURIComponent(value)
        }
     }
  });

  return '?' + query.join('&')
}

// ------------- 달력관련 ----------------------
function calendarSetData (date) {
      var year = date.getFullYear()
      var month = date.getMonth() + 1


      var nowDate = new Date(year, month-1, 1); // 이번달
      var firstDateWeek = nowDate.getDay(); // 이번달 첫번째 일
      var lastDay = new Date(
          year,
          month,
          0 // ex) 2020.08.0 > 2020.07.31 마지막날
        ).getDate();  //이번달 마지막 일

      var preDate = new Date(year, month - 2, 1); // 지난달
      var preLastDay = new Date(
          year,
          month - 1,
          0
        ).getDate();  //지난달 마지막 일
      var preLastFirstDate = preLastDay - (firstDateWeek - 1)

      var nextDate = new Date(year, month, 1); // 다음달

      var currentDay = preLastFirstDate

      var arr = []
      var monthType = 'pre'

      if (firstDateWeek === 0) {
        monthType = 'now'
        currentDay = 1
      }

      for (var i = 0; i < 6; i++) {
        if (i === 5 && monthType == 'next') {
          break
        }

        var params = {}
        params.rows = i
        params.day = []
        for (var j = 0; j < 7; j++) {

            var yyyymmdd = ''
            if (monthType === 'pre') { // 전달
              yyyymmdd = preDate.getFullYear() + _.padStart(preDate.getMonth() + 1, 2, '0') + _.padStart(currentDay,2,'0')
            } else if (monthType === 'now') { // 이번달
              yyyymmdd = nowDate.getFullYear() + _.padStart(nowDate.getMonth() + 1, 2, '0') + _.padStart(currentDay,2,'0')
            } else { // 다음달
              yyyymmdd = nextDate.getFullYear() + _.padStart(nextDate.getMonth() + 1, 2, '0') + _.padStart(currentDay,2,'0')
            }

            var tempDay = {
                type: monthType,
                week: calendarSetWeek(j),
                yyyymmdd:  yyyymmdd,
                day: currentDay,
                year: yyyymmdd.substring(0,4),
                month: yyyymmdd.substring(4,6),
                day_pad: yyyymmdd.substring(6),

            }

            if (monthType === 'pre' && currentDay === preLastDay) {
                currentDay = 1
                monthType = 'now'
            } else if (monthType === 'now' && currentDay === lastDay) {
                currentDay = 1
                monthType = 'next'
            } else {
              currentDay++
            }

            // console.log('monthType>>>', currentDay, monthType, preLastDay, lastDay)
            params.day.push(tempDay)
        }
        arr.push(params)
      }
      return arr;
};

function calendarSetWeek (param) {
    if (param === 0) return 'sunday'
    if (param === 1) return 'monday'
    if (param === 2) return 'tuesday'
    if (param === 3) return 'wednesday'
    if (param === 4) return 'thursday'
    if (param === 5) return 'friday'
    if (param === 6) return 'saturday'
}

// is_double_pop 이중 팝업창 dimme 제거 방지
function layer_popup_convert(el, is_double_pop){
    var $body = $('html, body'),
          $dimmed = $('.dimmed');

		var $el = $(el);
    $el.fadeIn();
    $dimmed.addClass('pop');
    $dimmed.css('z-index','10');

    if (!is_double_pop) {
      $dimmed.fadeIn();
      $body.css('overflow','hidden');
      $('body').addClass('scroll-lock');
    }

    var $elWidth = ~~($el.outerWidth()),
      $elHeight = ~~($el.outerHeight()),
      docWidth = $(document).width(),
      docHeight = $(document).height();

    // 화면의 중앙에 레이어를 띄운다.
    if ($elHeight < docHeight || $elWidth < docWidth) {
      $el.css({
        marginTop: -$elHeight /2,
      })
    } else {
      $el.css({top: 0, left: 0});
    }
    $el.find('.pop-close, .close').off() // 기존 이벤트 초기화
    $el.find('.pop-close, .close').click(function(){
      if (!is_double_pop) {
          $dimmed.fadeOut();
          $dimmed.removeClass('pop');
          $body.css('overflow','inherit');
          $('body').removeClass('scroll-lock');
          $('body').css('width','');
          $('.dimmed').css('z-index','5');
      }
      $el.fadeOut();
      return false;
    });
    //scroll table tutorial
    $el.find('.pop-layer .tutorial').off()
    $('.pop-layer .tutorial').on('click',function() {
      $(this).fadeOut();
    });

    layerPosConvert() // 팝업 위치 설정
}
function layerPosConvert() {
  var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
	if( !(isMobile) ) {
		$('.pop-layer').css('left','calc(50% - 9px)');
		if($(window).width() < 768 && $('.pop-layer').hasClass('full')) {
			$('.pop-layer').css('left',0);
		}
		if( $(window).width() <= $('.pop-layer').width() ) {
			$('.pop-layer').css('left',0);
		}
	}

	$('.pop-layer').each(function() {
		if( window.outerWidth > 767 ) {
			if( $(this).find('.pop-conts').outerHeight() > 640 && !$(this).hasClass('max') && !$(this).hasClass('sm') ) {
				$(this).css('height',645);
				$(this).find('.pop-conts').css('padding-bottom',40);
			}
		} else {
			if ( $(this).hasClass('full') ) {
			}
		}
	});
}


function setDatepicker() {
  // date picker
  $( ".calendar").datepicker({
  		dateFormat: "yy-mm-dd",
  		showOtherMonths: true, // 월 1일 이전, 월 말일 이후 빈칸에 이전달, 다음달 날짜 출력 여부
  		selectOtherMonths: true, // 다른 달도 출력시 선택가능 여부. 디폴트는 false
  		monthNames: ['01','02','03','04','05','06','07','08','09','10','11','12'], //달력의 월 부분 Tooltip 텍스트
  		monthNamesShort: ['01','02','03','04','05','06','07','08','09','10','11','12'], // 월의 한글 형식.
  		dayNamesMin: ['S','M','T','W','T','F','S'] ,//달력의 요일 부분 텍스트
  		showMonthAfterYear: true, // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
  		yearSuffix: ".", // 년도 옆 문구
  		showButtonPanel: true,
  		closeText: 'close',
  		changeMonth: true,
  		changeYear: true,
  		numberOfMonths: [1,1],
  		beforeShow: function(input) {
          setDatepickerClearBtn(input)
      },
      onChangeMonthYear: function() {
          setDatepickerClearBtn(this)
      },
      onClose: function() {
        setVModelData(this)
      }
  });
  $( ".calendar").attr("autocomplete", "off"); // 달력 자동입력 off
  var $calendar = $('.calendar, .calendar-from, .calendar-to');
  if( $(window).width() < 1440) {
    $calendar.attr('readonly','readonly')
  }
}

// jquery 강제 입력 이벤트
function setVModelData(element, type) {
  if (!type) type = 'input'
  var event;
  if(typeof(Event) === 'function') {
      event = new Event(type);
  } else {
      // ie 11 전용
      event = document.createEvent('Event');
      event.initEvent(type, true, false);
  }
  element.dispatchEvent(event);
}

function setDatepickerClearBtn(element) {
  setTimeout(function () {
      var buttonPane = $(element)
          .datepicker("widget")
          .find(".ui-datepicker-buttonpane");

      var btn = $('<button class="ui-datepicker-clear ui-state-default ui-priority-secondary ui-corner-all" type="button">Clear</button>');
      btn.off("click").on("click", function () {
          element.value = '';
      });
      btn.appendTo(buttonPane);
  }, 1);
}

// mix form post submit
// parameter만 있는 경우 - 검색 버튼으로 검색
// parameter + keyword 있는 경우  - 상세페이지 이동
// keyword만 있는 경우 - 상세페이지 에서 리스트로 이동
function commonFormSubmit (url, parameter, keyword, option){
  var form = document.createElement("form");

  form.setAttribute("method", (!_.isEmpty(option) && !_.isEmpty(option.method))?option.method: "POST");
  form.setAttribute("action", url);
  form.setAttribute("target", (!_.isEmpty(option) && !_.isEmpty(option.target))?option.target: "_self");
  if (!_.isEmpty(keyword)) {
      if (!_.isEmpty(parameter)) {
          // 상세 페이지 이동
          if (typeof keyword === 'object') {
            parameter.keyword = setKeyword(keyword) // json > url params
          } else {
            parameter.keyword = keyword
          }
      } else {
          // 리스트 검색
          parameter = commonGetUrlParams(keyword) // url params > json
      }
  }

  //히든으로 값을 주입시킨다.
  for(var key in parameter) {
      if (parameter[key] !=null && !_.isEmpty(String(parameter[key]))) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", parameter[key]);
        form.appendChild(hiddenField);
      }
  }
  loadingOut()

  if (url != '/common/Download') {
    loading()
  }

  document.body.appendChild(form);
  form.submit();
}
// url params > json
function commonGetUrlParams (url) {
    var params = {}
    if (!_.isEmpty(url)) {
      var qs = url.substring(url.indexOf('?')+1).split('&');
      for(var i = 0; i < qs.length; i++){
        qs[i] = qs[i].split('=');
        if (qs[i] != '' && qs[i][1] != '') {
          params[qs[i][0]] = decodeURIComponent(qs[i][1]);
        }
      }
    }
    return params;
}

/*
경고창 오픈
  alertPopup('테스트 문구 alert').then(function(){
  })

  alertPopup('테스트 문구', 'confirm').then(function(){
  }).catch(function(err) {})

  alertPopup('테스트 문구', 'alert')
  alertPopup('테스트 문구', 'alert', callback)
  alertPopup('테스트 문구', 'confirm', callback)
*/
function alertPopup(msg, type , callback) {
  if ($('.dimmed.pop').length > 0) $(".double-layer-wrap .pop-layer").css('z-index', '10') // 이중 팝업창
  return new Promise(function (resolve, reject){
      $('#alertpop .half.close').hide()
      $('#alertpop .full.close').hide()
      $('#alertpop #alert-pop-msg').text('')
      $('#alertpop #alert-pop-msg *').remove()
      $('#alertpop').css('height', 'auto')
      $('#alertpop .pop-conts').removeAttr('style')

      $('#alertpop #alert-pop-msg').append(msg)

      if (type === 'confirm') {
        $('#alertpop .half.close').show()
      } else {
        $('#alertpop .full.close').show()
      }
      if ($('.dimmed.pop').length > 0 && $(".double-layer-wrap .pop-layer").length > 0) {
        layer_popup_convert('#alertpop', true); // 이중 팝업창
      } else {
        layer_popup_convert('#alertpop'); // lasmch_common.js 강제로 팝업 open
      }

      $('#alertpop #layer_confirm_ok, #layer_alert_ok').on('click',function() {
        if (callback) {
          callback()
        }
        callback = null
        resolve(true)
        if ($('.dimmed.pop').length > 0) $(".double-layer-wrap .pop-layer").css('z-index', '12') // 이중 팝업창
        $('#alertpop #layer_confirm_ok, #layer_alert_ok').off('click') // 기존 이벤트 초기화
      });
      $('#alertpop #layer_confirm_cancel').on('click',function() {
        reject(false)
        if ($('.dimmed.pop').length > 0) $(".double-layer-wrap .pop-layer").css('z-index', '12') // 이중 팝업창
        $('#alertpop #layer_confirm_cancel').off('click') // 기존 이벤트 초기화
      });

  });
}

// page_post 이외 사용할때
/*var option = {
                             current: 1,
                             total: 1444,
                             count_page: 13 // 13개 리스트 씩
                           }*/
function setPagenate(option) {
  var page_params = { first:1, prev: 1, next: 1, current: 1, total: 1, last: 1,
                      is_before: false, is_next: false, pages: []}
  var current = option.current
  var total = option.total
  var count_page = option.count_page // 3개 리스트 씩
  var page_group = 7 // 7 페이지 씩
  var total_page = Math.ceil(total / count_page) // 총 페이지 수 올림
  var start_range = 0
  var end_range = 0
  var is_before = false
  var is_next = false
  if (total_page <= 0) total_page = 1

  if (total_page >= current) {
    var group_no = current / page_group + (current % page_group > 0 ? 1 : 0);
    end_range = Math.floor(group_no) * page_group; // 버림
    start_range = end_range - (page_group - 1);
    is_before = current > 1 && current <= total_page;
    is_next = current > 0 && current < total_page;
  }

  if (total_page <= end_range) end_range = total_page

  page_params.first = 1
  page_params.prev = current - 1
  page_params.current = Number(current)
  page_params.total = total
  page_params.next = end_range + 1
  page_params.last = total_page
  page_params.is_before = is_before
  page_params.is_next = is_next

  page_params.start_range = start_range
  page_params.end_range = end_range
  for (var i = start_range; i <= end_range; i++) {
    page_params.pages.push(i);
  }
  return page_params
}

function isBrowserCheck() {
    var userAgent = navigator.userAgent;
    var reg = null;
    var browser = {
        name: null,
        version: null
    };
    userAgent = userAgent.toLowerCase();

    if (userAgent.indexOf("opr") !== -1) {
        reg = /opr\/(\S+)/;
        browser.name = "opera";
        browser.version = reg.exec(userAgent)[1];
    } else if (userAgent.indexOf("edge") !== -1) {
        reg = /edge\/(\S+)/;
        browser.name = "edge";
        browser.version = reg.exec(userAgent)[1];
    } else if (userAgent.indexOf("chrome") !== -1) {
        reg = /chrome\/(\S+)/;
        browser.name = "chrome";
        browser.version = reg.exec(userAgent)[1];
    } else if (userAgent.indexOf("safari") !== -1) {
        reg = /safari\/(\S+)/;
        browser.name = "safari";
        browser.version = reg.exec(userAgent)[1];
    } else if (userAgent.indexOf("firefox") !== -1) {
        reg = /firefox\/(\S+)/;
        browser.name = "firefox";
        browser.version = reg.exec(userAgent)[1];
    } else if (userAgent.indexOf("trident") !== -1) {
        browser.name = "ie";

        if (userAgent.indexOf("msie") !== -1) {
            reg = /msie (\S+)/;
            browser.version = reg.exec(userAgent)[1];
            browser.version = browser.version.replace(";", "");
        } else {
            reg = /rv:(\S+)/;
            browser.version = reg.exec(userAgent)[1];
        }
    }

    return browser;
}

function getCookie( name ){
    var nameOfCookie = name + "=";
    var x = 0;
    while ( x <= document.cookie.length ) {
        var y = (x+nameOfCookie.length);
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;
        if ( x == 0 ) break;
    }
    return "";
}

function setCookie( name, value, expiredays ) {
  if (!_.isEmpty(name)) {
    if (_.isEmpty(value)) value = 'done'
    if (_.isUndefined(expiredays)) expiredays = 1
    var todayDate = new Date();
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
  }
}

Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}

Date.prototype.format = function (f) {

    if (!this.valueOf()) return " ";



    var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];

    var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var d = this;



    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {

        switch ($1) {

            case "yyyy": return d.getFullYear(); // 년 (4자리)

            case "yy": return (d.getFullYear() % 1000).zf(2); // 년 (2자리)

            case "MM": return (d.getMonth() + 1).zf(2); // 월 (2자리)

            case "dd": return d.getDate().zf(2); // 일 (2자리)

            case "KS": return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)

            case "KL": return weekKorName[d.getDay()]; // 요일 (긴 한글)

            case "ES": return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)

            case "EL": return weekEngName[d.getDay()]; // 요일 (긴 영어)

            case "HH": return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)

            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2); // 시간 (12시간 기준, 2자리)

            case "mm": return d.getMinutes().zf(2); // 분 (2자리)

            case "ss": return d.getSeconds().zf(2); // 초 (2자리)

            case "a/p": return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분

            default: return $1;

        }

    });

};



String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };

String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };

String.prototype.replaceAll = function(searchStr,replaceStr){
    return this.split(searchStr).join(replaceStr);
}

Number.prototype.zf = function (len) { return this.toString().zf(len); };


var oEditors = [];
var sLang = "ko_KR";
function setSmartEditor() {
    // smart-editor-desc
    if ($("#smart-editor-desc").length > 0) {
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "smart-editor-desc",
            sSkinURI: "/assets/vendor/smarteditor2/SmartEditor2Skin.html",
            htParams : {
                bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                //bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
                //aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
                fOnBeforeUnload : function(){
                    //alert("완료!");
                },
                I18N_LOCALE : sLang
            }, //boolean
            fOnAppLoad : function(){
                //예제 코드
                //oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
            },
            fCreator: "createSEditor2"
        });
    }
}


function getSmartEditorShowHTML() {
    return oEditors.getById["smart-editor-desc"].getIR();
}
