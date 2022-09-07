create table PRO_PARTIDA
(
  part_id         NUMBER not null,
  part_dificultad VARCHAR2(1) not null,
  part_version    NUMBER not null,
  part_timer      NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column PRO_PARTIDA.part_id
  is 'Id de la partida';
comment on column PRO_PARTIDA.part_dificultad
  is 'Dificultad de la partida';
comment on column PRO_PARTIDA.part_version
  is 'Version del registro de la partida';
comment on column PRO_PARTIDA.part_timer
  is 'Timer de la partida';
alter table PRO_PARTIDA
  add constraint PRO_PARTIDA_PK primary key (PART_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_PARTIDA
  add constraint PRO_PARTIDA_CK01
  check (part_dificultad in ('J','A'));

create table PRO_USUARIO
(
  usu_id              NUMBER not null,
  usu_nombre          VARCHAR2(30) not null,
  usu_partidasjugadas NUMBER,
  usu_partidasganadas NUMBER,
  usu_estado          VARCHAR2(1),
  usu_version         NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column PRO_USUARIO.usu_id
  is 'Id del Usuario';
comment on column PRO_USUARIO.usu_nombre
  is 'Nombre del usuario';
comment on column PRO_USUARIO.usu_partidasjugadas
  is 'Partidas jugadas por el usuario';
comment on column PRO_USUARIO.usu_partidasganadas
  is 'Partidas ganadas por el usuario';
comment on column PRO_USUARIO.usu_estado
  is 'Estado del usuario(A:Activo, I:Inactivo)';
comment on column PRO_USUARIO.usu_version
  is 'Version del registro de usuario';
create unique index PRO_USUARIO_UNQ01 on PRO_USUARIO (USU_NOMBRE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PRO_USUARIO
  add constraint PRO_USUARIO_PK primary key (USU_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PRO_USUARIO
  add constraint PRO_USUARIO_CK01
  check (usu_estado in ('A','I'));

create table PRO_LETRA
(
  let_id      NUMBER not null,
  let_letra   VARCHAR2(1) not null,
  let_dirimg  VARCHAR2(100) not null,
  let_puntos  NUMBER not null,
  let_version NUMBER not null,
  usu_id      NUMBER,
  part_id     NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column PRO_LETRA.let_id
  is 'Id de la letra vinculada';
comment on column PRO_LETRA.let_letra
  is 'Letra';
comment on column PRO_LETRA.let_dirimg
  is 'Direccion de la imagen de la letra';
comment on column PRO_LETRA.let_puntos
  is 'Cantidad de puntos de la letra';
comment on column PRO_LETRA.let_version
  is 'Version del registro de la letra';
comment on column PRO_LETRA.usu_id
  is 'Id del usuario vinculado a la letra';
comment on column PRO_LETRA.part_id
  is 'Id de la partida vinculada';
create index PRO_LETRA_UNQ01 on PRO_LETRA (USU_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
create index PRO_LETRA_UNQ02 on PRO_LETRA (PART_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_LETRA
  add constraint PRO_LETRA_PK primary key (LET_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_LETRA
  add constraint PRO_PARTLET foreign key (PART_ID)
  references PRO_PARTIDA (PART_ID);
alter table PRO_LETRA
  add constraint PRO_USULET_FK01 foreign key (USU_ID)
  references PRO_USUARIO (USU_ID);

create table PRO_CELDA
(
  cel_id      NUMBER not null,
  cel_posx    NUMBER not null,
  cel_posy    NUMBER not null,
  cel_version NUMBER not null,
  let_id      NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column PRO_CELDA.cel_id
  is 'Id de la celda';
comment on column PRO_CELDA.cel_posx
  is 'Posición x de la celda';
comment on column PRO_CELDA.cel_posy
  is 'Posición y de la celda';
comment on column PRO_CELDA.cel_version
  is 'Versión del registro de la celda';
comment on column PRO_CELDA.let_id
  is 'Id de la letra vinculada';
create index PRO_CELDA_UNQ01 on PRO_CELDA (LET_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_CELDA
  add constraint PRO_CELDA_PK primary key (CEL_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_CELDA
  add constraint PRO_LETCEL_FK01 foreign key (LET_ID)
  references PRO_LETRA (LET_ID);

create table PRO_PALABRA
(
  pal_id      NUMBER not null,
  pal_palabra VARCHAR2(50) not null,
  pal_version NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column PRO_PALABRA.pal_id
  is 'Id de la palabra
';
comment on column PRO_PALABRA.pal_palabra
  is 'La palabra';
comment on column PRO_PALABRA.pal_version
  is 'La version del registro de la palabra';
create unique index PRO_PALABRA_UNQ01 on PRO_PALABRA (PAL_PALABRA)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PRO_PALABRA
  add constraint PRO_PALABRA_PK primary key (PAL_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

create table PRO_USUPART
(
  uxp_idusu  NUMBER not null,
  uxp_idpart NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column PRO_USUPART.uxp_idusu
  is 'Id del usuario.';
comment on column PRO_USUPART.uxp_idpart
  is 'Id de la partida';
alter table PRO_USUPART
  add constraint PRO_USUPART_PK primary key (UXP_IDUSU, UXP_IDPART)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table PRO_USUPART
  add constraint PRO_USUPART_FK01 foreign key (UXP_IDUSU)
  references PRO_USUARIO (USU_ID);
alter table PRO_USUPART
  add constraint PRO_USUPART_FK02 foreign key (UXP_IDPART)
  references PRO_PARTIDA (PART_ID);

alter table PRO_LETRA disable constraint PRO_PARTLET;
alter table PRO_LETRA disable constraint PRO_USULET_FK01;
alter table PRO_CELDA disable constraint PRO_LETCEL_FK01;
alter table PRO_USUPART disable constraint PRO_USUPART_FK01;
alter table PRO_USUPART disable constraint PRO_USUPART_FK02;
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (1, 'AnthonyahAHAHAH', 0, 0, 'A', 2);
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (6, 'Fermin', 0, 0, 'A', 1);
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (7, 'HHHH', 0, 0, 'A', 1);
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (8, 'JulitaHZ', 0, 0, 'A', 2);
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (3, 'Kevin', 0, 0, 'A', 1);
insert into PRO_USUARIO (usu_id, usu_nombre, usu_partidasjugadas, usu_partidasganadas, usu_estado, usu_version)
values (4, 'Steven', 0, 0, 'A', 1);
commit;
alter table PRO_LETRA enable constraint PRO_PARTLET;
alter table PRO_LETRA enable constraint PRO_USULET_FK01;
alter table PRO_CELDA enable constraint PRO_LETCEL_FK01;
alter table PRO_USUPART enable constraint PRO_USUPART_FK01;
alter table PRO_USUPART enable constraint PRO_USUPART_FK02;
