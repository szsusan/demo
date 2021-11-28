local list = KEYS;
local el = {}
for i, v in ipairs(list) do
    local e = redis.call('HEXISTS', ARGV[1], v)
    if e == 0 then
        e = redis.call('HEXISTS', ARGV[2], v)
    end
    table.insert(el, e);
end
return el