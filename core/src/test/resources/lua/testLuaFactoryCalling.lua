--
-- Created by IntelliJ IDEA.
-- User: Klayton
-- Date: 7/26/2018
-- Time: 11:02 PM
-- To change this template use File | Settings | File Templates.
--


value = 1;
function testFunction()

    local obj = Factory:createObj("name");
    --local obj = Factory:createObj("blankname");
--    local obj = Factory:createObj();

    obj:setVal1("1val" .. tostring(value));
    value = value + 1
    obj:setVal2("2");

    if obj:getBool() then
        obj:setVal3(false);
    else
        obj:setVal3(true);
    end

    return obj;

end

