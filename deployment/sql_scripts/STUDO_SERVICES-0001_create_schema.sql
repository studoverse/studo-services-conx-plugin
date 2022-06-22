begin
  dboDDL.createUserIfNotExists(
      pUsername          => 'STUDO_SERVICES'
   , pDefaultTableSpace => 'USERS'
);
end;
/
