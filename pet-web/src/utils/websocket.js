
import {Message} from 'element-ui'


let url = 'ws://127.0.0.1:8888/websocket'

var ws
// 重连定时器实例
var tt
// websocket重连开关
var websocketswitch = false


var websocket = {
    // websocket建立连接
    Init(roomId,userId) {

        // 判断浏览器是否支持websocket
        if (!'WebSocket' in window) {
            Message({
                type: 'error',
                message: '您的浏览器不支持websocket'
            })
            return
        }

        // 创建websocket实例
        ws = new WebSocket(url + '/' + roomId+'/'+userId)
        // 监听websocket连接
        ws.onopen = function () {
            console.log("websocket 连接成功")
        }
        // 监听websocket连接错误信息
        ws.onerror = function (e) {
            console.log('重连开关', websocketswitch)
            console.log('数据传输发生错误', e)
            Message({
                message: '数据传输发生错误',
                type: 'error'
            })

            // 打开重连
            reconnect(roomId,userId)
        }
    },
    // websocket连接关闭方法
    Close() {
        return new Promise(resolve=>{
            //关闭断开重连机制
            websocketswitch = true
            ws.close()
            resolve()
        })
    },
    // websocket发送信息方法
    Send(data) {
        // 处理发送数据JSON字符串
        let msg = JSON.stringify(data)
        // 发送消息给后端
        ws.send(msg)
    },
    // 暴露websocket实例
    getwebsocket() {
        return ws
    }
}

// 重连方法
function reconnect(roomId,userId) {
    // 判断是否主动关闭连接
    if (websocketswitch) {
        return
    }
    // 重连定时器-每次websocket错误方法onerror触发他都会触发
    tt && clearTimeout(tt)
    tt = setTimeout(function () {
        console.log('执行断线重连...')
        websocket.Init(roomId,userId)
        websocketswitch = false
    }, 4000)
}

// 暴露对象
export default websocket