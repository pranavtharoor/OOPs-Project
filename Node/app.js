const net = require('net')

const server = net.createServer()

const port = 5003
server.listen(port, () => {
	console.log('Listening on port ' + port)
})

activeSockets = {}
let socketNumber = 0

server.on('connection', socket => {

        socketNumber++
        socket.nickname = socketNumber
        activeSockets[socketNumber] = socket

        console.log('New connection: ' + socket.nickname)

        socket.on('data', data => {
                console.log(socket.nickname + ': ' + data.toString('utf-8'))
                for(let socketNum in activeSockets)
                        if(activeSockets[socketNum].nickname != socket.nickname)
                                activeSockets[socketNum].write(data)
        })

        socket.on('close', data => {
                console.log('Connection closed: ' + socket.nickname)
                delete activeSockets[socket.nickname]
        })
})