# WSPro Web Client

The Web Client module holds an ***Experimental*** browser client to play WSPro over a network.

**NOTE: This is currently not in a usable state**

### Building

#### Pre-requisites:

 - NPM (or yarn)


#### Build 
1. install modules
    1. `cd web-client`
    2. `npm install`
2. Generate Protobuf definitions
    1. download [protoc.exe](https://github.com/protocolbuffers/protobuf/releases/latest) and [protoc-gen-grpc-web.exe](https://github.com/grpc/grpc-web/releases/latest) and copy into the `web-client\tools` folder
    2. run `protocGen.bat`(Windows) or `protoc.sh` on Linux
3. Fix Protobuf definitions ([why?](https://github.com/grpc/grpc-web/issues/447#issuecomment-492063767))
    1. navigate to `src/game/grpc/generated`
    2. add `/* eslint-disable */` to the top of every file
4. `npm run-script build`

#### Run
`npm run-script serve`\
Look at output to see running port     
    
    
### Contributing

Join the [Discord Server](https://discord.gg/hkW5Xwc) for all questions relating to development, contributing, bug reports, and feature requests.

 
