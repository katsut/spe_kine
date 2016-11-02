function modify( rec, adId, time, user )
  local meth = "update_stats()";
  trace("[ENTER]<%s> Value(%s) valType(%s)", meth, tostring(newValue),type(newValue));
  local rc = 0;

  if( not aerospike:exists( rec ) ) then
    rc = aerospike:create( rec );
    if( rc == nil or rec == nil ) then
      warn("[ERROR]<%s> Problems creating record", meth );
      error("ERROR creating record");
    end
    info("<CHECK><%s> Check Create Result(%s)", meth, tostring(rc));
    rec["id"] = adId;
    rec["impression"] = 1;
    rec["lastImpressionAt"] = time;
    rec["lastImpressionUser"] = user;
  else
    local count = 1;
    if( rec["impression"] == nil ) then  -- Impressionをインクリメント
      count = 1;                  -- this is the first one
    else
      count = rec["impression"];
      count = count + 1;          -- increment the value
    end

    if( rec["lastImpressionAt"] == nil or true ) then -- 日付の大小比較は面倒なので省略
        rec["lastImpressionAt"] = time;
        rec["lastImpressionUser"] = user;
    else
        rec["lastImpressionAt"] = time;
        rec["lastImpressionUser"] = user;
    end
  end -- else record already exists

  -- All done -- update the record (checking for errors on update)
  rc = aerospike:update( rec );
  if( rc ~= nil and rc ~= 0 ) then
    warn("[ERROR]<%s> record update: rc(%s)", meth,tostring(rc));
    error("ERROR updating the record");
  end
  rc = 0; -- safety, in case rc == nil.  But, no error here.
  trace("[EXIT]:<%s> RC(%d)", meth, rc );
  return rc;
end -- update_stats()
