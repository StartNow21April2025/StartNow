import React, { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const Community = () => {
  const [username, setUsername] = useState("");
  const [messageInput, setMessageInput] = useState("");
  const [messages, setMessages] = useState([]);
  const stompClientRef = useRef(null);
  const hasSubscribedRef = useRef(false);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      console.log("Connected to WebSocket");

      // Subscribe only once
      if (!hasSubscribedRef.current) {
        stompClient.subscribe("/topic/group", (response) => {
          const msg = JSON.parse(response.body);
          setMessages((prev) => [...prev, `${msg.sender}: ${msg.content}`]);
        });
        hasSubscribedRef.current = true;
      }
    });

    stompClientRef.current = stompClient;

    // Load previous group messages
    fetch("http://localhost:8080/api/group/messages")
      .then((res) => res.json())
      .then((data) => {
        const loaded = data.map((msg) => `${msg.sender}: ${msg.content}`);
        setMessages(loaded);
      });

    return () => {
      if (stompClientRef.current) {
        /*  stompClientRef.current.disconnect(); */
      }
    };
  }, []);

  const sendMessage = () => {
    if (!username || !messageInput) {
      alert("Please enter username and message.");
      return;
    }

    if (!stompClientRef.current || !stompClientRef.current.connected) {
      alert("WebSocket not connected.");
      return;
    }

    stompClientRef.current.send(
      "/app/sendGroupMessage",
      {},
      JSON.stringify({
        sender: username,
        content: messageInput,
        receiver: "group",
      })
    );

    setMessageInput("");
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Group Chat</h2>

      <input
        type="text"
        placeholder="Enter your name"
        className="border p-2 mb-2 block w-full"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <input
        type="text"
        placeholder="Enter message"
        className="border p-2 mb-2 block w-full"
        value={messageInput}
        onChange={(e) => setMessageInput(e.target.value)}
      />

      <button
        onClick={sendMessage}
        className="bg-blue-500 text-white px-4 py-2 rounded mb-4"
      >
        Send
      </button>

      <ul className="list-disc pl-5">
        {messages.map((msg, idx) => (
          <li key={idx} className="mb-1">
            {msg}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Community;
