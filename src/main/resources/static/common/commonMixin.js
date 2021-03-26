
var commonMixin = {
    data: function() {
          return {
            is_pop_show: false,
          }
    },
    computed: {},
    methods: {
      // 날짜 비교
      compareDates: function(str_dt, end_dt, option) {
        if (_.isEmpty(str_dt) ||_.isEmpty(end_dt)) return false
        // safari 체크
        if (isBrowserCheck().name === 'safari') {
          str_dt = str_dt.replaceAll('-', '/')
          end_dt = end_dt.replaceAll('-', '/')
        }
        var str_dt_time = new Date(str_dt).getTime()
        var end_dt_time = new Date(end_dt).getTime()
        // 단순 날짜 비교 true,false return
        if (_.isEmpty(option)) {
          if (str_dt_time > end_dt_time) {
            return false
          }
          return true
        } else {
          // 비교 날짜 차이
          // min, hour, day으로 시간 return
          if (option === 'min') { // 분 날짜 차이
            return (str_dt_time - end_dt_time) / 1000 / 60;
          } else if (option === 'hour') { // 시간 날짜 차이
            return (str_dt_time - end_dt_time) / 1000 / 60 / 60;
          } else { // 일 날짜 차이
            return (str_dt_time - end_dt_time) / 1000 / 60 / 60 / 24;
          }
        }
      },
      // 정규식 체크
      regexCheck: function(val, regex) {
        if (!_.isEmpty(val)) {
          if (_.isUndefined(regex)) regex = /[^A-Za-z0-9]/gi // default 영문숫자만 입력
          val = val.toString().replace(regex, '')
          return _.toUpper(val) // 대문자 변환
        } else {
          return ''
        }
      },
      // 천단위 콤마
      thsndComas: function(val, max) {
        if (_.isUndefined(max)) max = 9999999999 // 기본적으로 천단위 들어가는건 거의 10자리가 끝
        val = val.toString().replace(/[^0-9]/gi, '')
        if (Number(val) > max) val = max
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
      },
      // flt_num 4글자 미만 0붙여서 4자로 만들기
      setPad: function(object, item) {
        if (!_.isUndefined(object) && !_.isUndefined(item)) {
          var trim_val= this[object][item].trim()
          if (!_.isEmpty(trim_val)) {
            this[object][item] = _.padStart(trim_val, 4, '0');
          }
        } else {
          var trim_val = this.params.flt_num.trim()
          if (!_.isEmpty(trim_val)) {
            this.params.flt_num = _.padStart(trim_val, 4, '0');
          }
        }

      },
      // keyword와 params merge하기
      setParams: function () {
        if (!_.isEmpty(this.params) && !_.isEmpty(this.keywords)) {
          this.params = _.merge(this.params, this.keywords)
        }
      },
      // 강제 팝업창 open element 새로 생성
      forcePopup: function (popId) {
        this.is_pop_show = true
        this.$nextTick(function () {
          layer_popup_convert(popId); // lasmch_common.js 강제로 팝업 open
        })
      },
      // 엑셀 파일 다운로드
      // ex) @click="mixinGetExcel('/meal', params)"
      mixinGetExcel: function(url, params, addParams){
        var data = _.cloneDeep(params)
        data = _.merge(data, addParams)
        data.page = 1
        data.fetchSize = 100
        location.href = url + '/excel' + setKeyword(data)
      },
      /* s3 파일 올린거 키값으로 찾아서 a 테그로 오픈하기 */
      // 사용 할려면 src_path 변수 선언 및
      // <a :href="src_path" id="image-view" target="_blank" class="cp"></a> 셋팅
      s3fileView: function(s3_key_path) {
        var self = this
        if (!_.isEmpty(s3_key_path)) {
          axios.post('/common/file-view', {path: s3_key_path}).then(function(res){
            if (res.status == 200) {
              self.src_path = res.data
              // safari에서 src 적용 시간 차이 때문에 안넣으면 오픈이 안됨
              setTimeout(function() {

                var browser = isBrowserCheck()
                // safari 저 버번 브라우저 인경우
                if (browser.name === 'safari' && isBrowserCheck().version.substring(0,1) === '5') {
                    // First create an event
                    var click_ev = document.createEvent("MouseEvents");
                    // initialize the event
                    click_ev.initEvent("click", true /* bubble */, true /* cancelable */);
                    // trigger the event
                    document.getElementById("image-view").dispatchEvent(click_ev);

                } else {
                    document.getElementById('image-view').click();
                }
              }, 100)
            }
          })
        }
      }
    },
    created: function () {
      this.setParams()
    },
    mounted: function () {
      // setDatepicker() // lasmch_common.js
    }
}