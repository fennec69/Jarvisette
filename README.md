## Communication Outputs <=> Server

1. Output open connection to the server
2. Output send supported services list
3. Server Add output in output list and services
4. Server send messages to output when needed

### Light Output communication
Order : {"action":"light", "power":"0/1"}

### DimLight Output communication
Order : {"action":"dimlight", "dimpercent":"0-100"}
