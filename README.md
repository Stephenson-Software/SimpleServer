# SimpleServer
A simple java multi-server that allows clients to connect and request the square of a number.

## Layout

The sources live under `src/`, and each directory below it matches its package name.

| Class | Role |
|-------|------|
| `SimpleServer.server.ServerApp` | Entry point for the server. Binds port `2000`. |
| `SimpleServer.server.MultiServer` | Accepts connections and starts one `MSThread` for each. |
| `SimpleServer.server.MSThread` | Reads each request line from its connection and writes back the response. |
| `SimpleServer.server.Protocol` | Turns a request into a response. |
| `SimpleServer.client.ClientApp` | Entry point for the client. Reads numbers from standard input, one per line, until the line `done`, and prints the server's `answer` or `reason` for each. |
| `SimpleServer.client.Client` | Holds the client's socket and its reader and writer. |
| `SimpleServer.Message` | The wire format shared by both ends. Also has a `main` that writes a message and parses it back. |

The client's hostname, `Walter`, and its port, `2000`, are both hardcoded in
`SimpleServer.client.ClientApp`, so the client has to be edited before it can reach a server on
any other host.

## Protocol

The server binds port `2000`, which is hardcoded in `SimpleServer.server.ServerApp`. Each
connection carries one newline-terminated message per line in both directions.

### Message format

A message is a sequence of `key=value` pairs, each one terminated by a comma:

```
process=square,number=10,
```

The format is implemented by `SimpleServer.Message`, and three of its properties are worth
knowing before it is used:

- The comma is what terminates a pair, so a final pair written without one is dropped by the
  parser rather than being read as the end of the message.
- Neither `=` nor `,` is escaped when a message is written, and both are read as delimiters, so
  no key and no value can carry either character.
- A key is appended to the message once per write, so a key written twice is emitted twice.

### Requests

| Key | Value |
|-----|-------|
| `process` | The operation being requested. `square` is the only value the server recognises. |
| `number` | The integer to be squared. Read only by the `square` process. |

### Responses

Every response carries `success`. On success it is followed by `answer`, and on failure by
`reason`.

| Key | Value |
|-----|-------|
| `success` | `true` or `false`. |
| `answer` | Sent only when `success` is `true`. Worded as the English sentence `The square of that number is <result>` rather than as a bare number. |
| `reason` | Sent only when `success` is `false`. One of the three strings below. |

| `reason` | Sent when |
|----------|-----------|
| `process not recognized` | `process` held a value other than `square`. |
| `Wrong type of input!` | `number` could not be parsed as an integer. |
| `Something went wrong when processing input.` | Processing threw, which includes a message that carried no `process` key at all. |
