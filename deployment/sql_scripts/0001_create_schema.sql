--------------------------------------------------------------------------------
-- SCRIPT: CREATE USER
--------------------------------------------------------------------------------

--    1. create user CO_KEYCLOAK
--    2. assign profile, tablespace
--    3. grant privilegs
--    4. unlock the account
--    5. register user in CO_INSTALL.CAMPUSONLINE_DB_USERS
--    6. set up statistics in CO_INSTALL.CAMPUSONLINE_DB_USERS

declare
    vNewUser                constant varchar2(20)   := '${stack.db.username}';
    vTableSpace             constant varchar2(20)   := 'TUGONLINE';

    vUserPasswd             varchar2(30);

    -- should user be registered in co_install.campusonline_db_users
    vRegisterUserInCoDBUser       constant boolean     := false;
    vUserFineGrainedStatistics    constant varchar2(1) := 'N'; -- has to be J or N


    vExecStmt         varchar2(32000) := null;

    function getDefaultUserPwd
        return varchar2
        is
begin
for rDefaultSchemaPwd in (
            with default_schema_password as (
                select count(*)               count_pwd
                     ,ci.schema_password     default_password
                from co_sec.connect_information ci
                group by ci.schema_password
                order by count(*) desc
            )
            select ci.schema_name
            from co_sec.connect_information ci
            where ci.schema_password = (
                select dsp.default_password
                from default_schema_password dsp
                where rownum < 2
            )
            order by decode(ci.schema_name
                ,'TUG_NEW', 0
                ,'STUD', 1
                ,null
                ) nulls last
                   ,ci.schema_name
            )
            loop
                return co_sec.connectInformation.getSchemaPWD(rDefaultSchemaPwd.schema_name);
end loop;
end;

    procedure createNewUser
is
        vUserExists   boolean     := false;
begin
        DBMS_OUTPUT.Put_Line('INFO:   creating user ' || vNewUser || ' ...');
for cUser in
            (
            select u.username
            from sys.dba_users u
            where u.username = upper(vNewUser)
            )
            loop
                vUserExists := true;
                DBMS_OUTPUT.Put_Line('        ... user ' || vNewUser || ' already exists.');
                exit;
end loop;

        if not vUserExists then
            vExecStmt := 'create user ' || vNewUser || ' identified by "' || vUserPasswd || '"';
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

vExecStmt := 'alter user  ' || vNewUser || ' profile "DEFAULT"';
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

vExecStmt := 'grant connect, resource to ' || vNewUser;
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

vExecStmt := 'alter user ' || vNewUser || ' account unlock';
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

vExecStmt := 'alter user ' || vNewUser || ' default tablespace ' || vTableSpace;
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

vExecStmt := 'grant unlimited tablespace to  ' || vNewUser;
            DBMS_OUTPUT.Put_Line(vExecStmt);
execute immediate vExecStmt;

DBMS_OUTPUT.Put_Line('        ... user ' || vNewUser || ' created.');
end if;
end;

    procedure registerUserInCoDBUser
is
        vUserExists   boolean     := false;

begin
        DBMS_OUTPUT.Put_Line('INFO:   register user ' || vNewUser || ' in CAMPUSONLINE_DB_USERS ...');
for cCampusOnlineDBUser in
            (
            select cou.username
            from campusonline_db_users cou
            where cou.username = upper(vNewUser)
                for update nowait
            )
            loop
                vUserExists := true;
update campusonline_db_users cou
set (cou.gen_fine_grained_statistics) = vUserFineGrainedStatistics
where cou.username = cCampusOnlineDBUser.username;
DBMS_OUTPUT.Put_Line('        ... user ' || vNewUser || ' registered by UPDATE.');
end loop;

        if not vUserExists then
            insert into campusonline_db_users cou
            (cou.username
            ,cou.gen_fine_grained_statistics)
            values
            (upper(vNewUser)
            ,vUserFineGrainedStatistics);
            DBMS_OUTPUT.Put_Line('        ... user ' || vNewUser || ' registered by INSERT.');
end if;

commit;

exception
        when others then
            rollback;
            DBMS_OUTPUT.Put_Line('ERR:    user ' || vNewUser || ' was NOT registered in CAMPUSONLINE_DB_USERS');
end;

begin
    DBMS_OUTPUT.Put_Line(' ');
    DBMS_OUTPUT.Put_Line('INFO:   RUNNING SCRIPT - ' || vNewUser);
    vUserPasswd := getDefaultUserPwd();
    createNewUser;
    if vRegisterUserInCoDBUser then
        registerUserInCoDBUser;
end if;
end;
/
