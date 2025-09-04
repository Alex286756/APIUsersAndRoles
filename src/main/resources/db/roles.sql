CREATE TABLE IF NOT EXISTS public.roles
(
    uuid UUID,
    rolename character varying(255) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (uuid),
)
