export const WebSocketDestinations = {
  NOTIFICATIONS: `/topic/notifications`,
  GLOBAL_CHAT: `/topic/global-chat`,

  ALERT: (userID: string) => `/topic/alert/${userID}`,
  SEND_ALERT: (userID: string) => `/app/alert/${userID}`,

  ROOM_MESSAGE: (roomID: string) => `/topic/room/${roomID}/send`,
  SEND_MESSAGE: (roomID: string) => `/app/send/room/${roomID}`,

  ROOM_TYPING: (roomID: string) => `/topic/room/${roomID}/typing`,
  SEND_TYPING: (roomID: string) => `/app/typing/room/${roomID}`,

  ROOM_ENTER: (roomID: string) => `/topic/room/${roomID}/enter`,
  SEND_ENTER: (roomID: string) => `/app/enter/room/${roomID}`,

  ROOM_SEEN: (roomID: string) => `/topic/room/${roomID}/seen`,
  SEND_SEEN: (roomID: string) => `/app/seen/room/${roomID}`,
};
