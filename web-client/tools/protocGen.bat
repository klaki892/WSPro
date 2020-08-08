ECHO DOWNLOAD protoc.exe AND protoc-gen-grpc-web TO RUN THIS
protoc.exe ^
-I "..\..\core\src\main\proto" ^
-I "..\..\server\src\main\proto" ^
--plugin=protoc-gen-ts="..\node_modules\.bin\protoc-gen-ts.cmd" ^
--js_out=import_style=commonjs,binary:"..\src\game\grpc\generated" ^
--ts_out=service=grpc-web:"..\src\game\grpc\generated" ^
"..\..\core\src\main\proto\*.proto" "..\..\server\src\main\proto\*.proto"
ECHO finished.