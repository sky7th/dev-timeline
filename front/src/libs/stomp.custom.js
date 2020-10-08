import Stomp from 'stomp-websocket';

const customStomp = {
  over: (sock, headers={}) => {
    const stomp = Stomp.over(sock);
    stomp.defaultHeaders = headers;
    stomp.debug = () => {};
    
    return stomp;
  }
}

export default customStomp;