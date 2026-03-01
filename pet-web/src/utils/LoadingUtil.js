import {Loading} from 'element-ui'

export default {
  loading: function (message) {
    return Loading.service({
      lock: true,
      text: message,
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });
  }
}
