CREATE TABLE IF NOT EXISTS public.users
(
    uuid UUID,
    fio character varying(255) NOT NULL,
    phonenumber character varying(255),
    avatar character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (uuid),
    CONSTRAINT FKdgl33hy5qewqcrqi03u1que6t FOREIGN KEY (roles_uuid)
        REFERENCES public.roles (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
