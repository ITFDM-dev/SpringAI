# Email MCP Server

This module exposes a narrow MCP tool for sending recruiter-approved email content through Spring AI MCP over Streamable HTTP.

## Required environment variables

Set these before starting the server:

- `EMAIL_USERNAME`: Gmail address used to send email
- `EMAIL_APP_PASSWORD`: Gmail application password, not the normal account password

## Windows User environment variables

In Windows, set them for your user account:

1. Open System Properties > Environment Variables.
2. Add `EMAIL_USERNAME` with your Gmail address.
3. Add `EMAIL_APP_PASSWORD` with the Gmail app password.
4. Restart your terminal or IDE after saving.

## Run the server

From the repository root:

```powershell
./demo/mvnw -f email-mcp-server/pom.xml spring-boot:run
```

This starts the server on port `8083`.

## Expected behaviour

- Port: `8083`
- MCP transport: Streamable HTTP
- MCP tool: `sendEmail`
- Endpoint: `http://localhost:8083/mcp`
- Startup is successful when the app logs `Tomcat started on port 8083` and `Registered tools: 1`.

The Resume Assistant must not begin MCP tool discovery until this server is already running.

## Security warning

Never expose this local HTTP endpoint beyond localhost without authentication and authorisation. This server should remain private to trusted local clients only.

## Important reminder

Do not commit real email usernames, application passwords, or local secrets. Keep credentials in environment variables only.
