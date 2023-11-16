   let ws;
    new Vue({
        el: "#Vueapp",
        data() {
            return {
                isShowChat: false,
                chatMes: false,
                isOnline: true,
                username:"",
                toName: "",
                sendMessage: {
                    toName: "",
                    message: ""
                },
                inputMessage: "",
                historyMessage: [

                ],
                friendsList: [

                ],
                systemMessages : [

                ]
            }
        },
        delimiters: ["[[", "]]"],
        created() {
            this.init();
        },
        methods: {
            async init() {
                await axios.get("/getUsername").then(res => {
                    this.username = res.data;
                });

                //connect websocket
                ws = new WebSocket("ws://localhost:8080/chat");

                //link websocket event
                ws.onopen = this.onOpen;

                ws.onmessage = this.onMessage;
                ws.onerror = this.onError;
                ws.onclose = this.onClose;
            },
            showChat(name) {
                this.toName = name;

                let history = sessionStorage.getItem(this.toName);
                if (!history) {
                    this.historyMessage = [];
                } else {
                    this.historyMessage = JSON.parse(history);
                }
                //show on chat box
                this.isShowChat = true;
                //show chat with user
                this.chatMes = true;
            },
            //submit button
            submit() {
                this.sendMessage.toName = this.toName;
                this.historyMessage.push(JSON.parse(JSON.stringify(this.sendMessage)));
                sessionStorage.setItem(this.toName, JSON.stringify(this.historyMessage));
                ws.send(JSON.stringify(this.sendMessage));
                this.sendMessage.message = "";
            },
            onOpen() {
                this.isOnline = true;
            },
            onError(event) {
                console.error("WebSocket error:", event);
                this.errorMessage = "WebSocket connect error";
            },
            onClose() {
                this.isOnline = false;
            },
            onMessage(evt) {
                //receive data from system
                var dataStr = evt.data;
                //transfer to JSON
                var res = JSON.parse(dataStr);


               if(res.system) {

                    var names = res.message;
                    this.friendsList = [];
                    this.systemMessages = [];
                    for (let i = 0; i < names.length; i++) {
                        if(names[i] != this.username) {
                            this.friendsList.push(names[i]);
                            this.systemMessages.push(names[i]);
                        }
                    }
                }else {
                    //is not system data,display on chat box
                    var history = sessionStorage.getItem(res.fromName);
                    if (!history) {
                        this.historyMessage = [res];
                    } else {
                        this.historyMessage.push(res);
                    }
                    sessionStorage.setItem(res.fromName, JSON.stringify(this.historyMessage));
                }
            }
        }
    });

