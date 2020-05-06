
function SAO_S47_001_R.InitalizeCard(card)
    card:testPrint("nice weather we are having");
    card:setCardName("Shiro");

    local e1 = Effect.CreateEffect(card);
    e1:setTiming(ON_PLAY);

    card:registerEffect(e1);
end

--local e1 = TestFunction.testPrint("hello world");



--local e2 = aFunction("helloWorld");

--myJavaObject:addValue(10);