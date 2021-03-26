var fileuploadTemplate = {
    template:
        '  <!-- 파일이 첨부되어있지 않은 상태 -->'
        +'  <div class="attach-files">'
        +'      <div class="btn-file" v-show="index === list.length - 1" v-for="(file,index) in list">'
        +'        <label :for="\'upload\'+index" class="file-label">파일선택</label>'
        +'        <input'
        +'               type="file"'
        +'               :id="\'upload\'+index"'
        +'               class="file-upload"'
        +'               :data="file.file_name"'
        +'               @change="fileChangeHandle(file, index)"'
        +'               ref="uploadFile"'
        +'        >'
        +'      </div>'
        +'      <!-- //btn-file -->'
        +'      <div class="file-wrap">'
        +'        <div class="input-file" v-show="!_.isEmpty(file.file_name)" v-for="(file,index) in list">'
        +'          <p class="file-name" v-text="file.file_name"></p>'
        +'          <button class="btn-del" @click="delFileList(file, index)">delete</button> <!-- 첨부된 파일 삭제 -->'
        +'        </div>'
        +'      </div>'
        +'    </div>'
        +'  <!-- //attach-files -->'
    ,
    props: {
        params: {
           type: Object,
           required: false,
           default: function() {
             return {
               delfile: []
             }
           }
         },
        list: { // 파일 리스트
           type: Array,
           required: false,
           default: []
         },
    },
    methods: {
      // --------------- 파일 셋팅 -------------------------------------------------
      // 파일 이름 셋팅
      fileChangeHandle: function (i, index) {
        i.file_name = event.target.value
        this.setFileList()
      },
      // 파일 추가
      setFileList: function () {
        if(this.list.length >= 10) {
          alert("over 10 files. You can't insert or attach file any more.");
          return ;
        }
        this.list.push({ file_s3_key: '',file_name: '' })
      },
      // 파일 삭제
      delFileList: function (i, index) {
        if (i.file_s3_key != '') {
          this.params.delfile.push(i.file_s3_key)
        }
        this.$refs.uploadFile[index].value = ''
        this.list.splice(index, 1)
      },
      // --------------------------------------------------------------------------
    },
    mounted: function () {
      this.params.delfile = [] // 삭제 파일 추가
      this.setFileList()
    }
};
