echo DOWNLOAD protoc AND protoc-gen-grpc-web TO RUN THIS
./protoc \
-I ../../core/src/main/proto \
-I ../../server/src/main/proto \
--plugin=protoc-gen-ts=../node_modules/.bin/protoc-gen-ts \
--js_out=import_style=commonjs,binary:../src/game/grpc/generated \
--ts_out=service=grpc-web:../src/game/grpc/generated \
../../core/src/main/proto/*.proto \
../../server/src/main/proto/*.proto \
echo finished.