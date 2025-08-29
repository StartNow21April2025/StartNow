// Mock for sockjs-client
export default class SockJS {
  constructor(url) {
    this.url = url;
    this.readyState = 1;
    this.onopen = null;
    this.onmessage = null;
    this.onclose = null;
    this.onerror = null;
  }

  send() {
    // Mock send method
  }

  close() {
    if (this.onclose) {
      this.onclose();
    }
  }
}
