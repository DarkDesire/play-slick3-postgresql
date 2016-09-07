# --- !Ups

CREATE TABLE "FavouriteService"
(
    "userId" integer NOT NULL,
    "serviceId" integer NOT NULL,
    PRIMARY KEY ("serviceId", "userId")
)

# --- !Downs

DROP TABLE "FavouriteService";





