# SimpleServer
A simple java multi-server that allows clients to connect and request the square of a number.

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
