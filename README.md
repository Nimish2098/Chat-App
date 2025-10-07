# Real-Time Chat Application

A Java-based real-time chat application using Spring Boot, WebSocket/STOMP, and Swing. Allows multiple users to chat instantly and see active users in real-time.

## Features

- Real-time messaging between users
- Active user list updates dynamically
- User-friendly GUI with Java Swing
- Uses WebSocket with STOMP for messaging
- Lightweight and easy to run locally

## Technology Stack

- Backend: Java, Spring Boot
- WebSocket: Spring WebSocket, STOMP, SockJS
- Frontend: Java Swing
- Messaging: STOMP over WebSocket
- JSON Parsing: Jackson
- Build Tool: Maven

## Installation

1. Clone the repository:
    git clone https://github.com/yourusername/Real-Time-Chat-Application.git
    cd Real-Time-Chat-Application

2. Build and run the backend server
   mvn clean install
   mvn spring-boot:run
Server runs on: `http://localhost:8080/ws`

3. Run the client GUI:
- Open `App.java` in your IDE and run.
- Enter a username and start chatting.

## Usage

1. Start the backend server first.
2. Launch the client GUI.
3. Enter a unique username.
4. Type messages and press Enter or click Send.
5. Messages appear in real-time for all users.
6. Active users list updates when someone joins or leaves.


## Future Improvements

- User authentication system
- Private one-on-one chats
- Emoji and file sharing support
- Message persistence in a database
- Improved UI/UX with JavaFX or web frontend

## Contributing

1. Fork the repo
2. Create a branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m 'Add feature'`
4. Push branch: `git push origin feature-name`
5. Open a pull request


