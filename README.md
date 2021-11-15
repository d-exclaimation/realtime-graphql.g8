# Ahql & OverLayer GraphQL API Template

A [Giter8][g8] template for realtime GraphQL API, fully setup.

## What comes with the template

- HTTP and Websocket API
- Akka Typed Actors.
- Ahql Server handler for POST request.
- OverLayer Websocket Handler.
- Intuitive Akka HTTP Route.
- Apollo Sandbox Redirect for GraphQL IDE.
- Soda Code-first Schema.
- Dockerfile and dockerignore

## Dependencies

- Akka Actors (Typed) `Latest / 2.6.17`, for Actor System and peer dependecies.
- Akka Streams `Latest / 2.6.17`, for subscription based real-time data.
- Akka HTTP `Latest / 10.2.7`, for routing and handling HTTP and other protocol request.
- Akka Spray JSON `Latest / 10.2.7`, for JSON parsing.
- Akka HTTP CORS `1.1.2`, for CORS Middleware.
- Sangria `Latest / 2.1.5`, for GraphQL parsing, execution, etc.
- Sangria Spray JSON `1.0.2`, for GraphQL to JSON parsing.
- OverLayer `Latest / 1.0.1`, for GraphQL over Websockets.
- Ahql `Latest / 0.2.3`, for GraphQL Server and Client handling in Akka HTTP.
- Soda `Latest / 0.5.0`, for GraphQL Code-first schema definition.

Template license
----------------
Written in 2021 by d_exclaimation.

To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this
template to the public domain worldwide. This template is distributed without any warranty.
See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
