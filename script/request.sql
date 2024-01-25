
--Nombre d'annonce active
select count(id) from annonce where etat = 5;


--Nombre de nouvelle annonce par mois, par ans(graphe)
--miankina am etat
--BOTH
SELECT  EXTRACT(YEAR FROM date_publication) AS annee,  EXTRACT(MONTH FROM date_publication) as mois, COUNT(*) AS nombre_annonces
FROM "public".annonce WHERE annonce.etat = 20
GROUP BY EXTRACT(YEAR FROM date_publication) ,  EXTRACT(MONTH FROM date_publication)
ORDER BY annee , mois;

--MONTH
SELECT  mois, COALESCE(COUNT(a.id), 0) AS nombre_annonces FROM generate_series(1, 12) mois
LEFT JOIN "public".annonce a ON EXTRACT(MONTH FROM a.date_publication) = mois AND a.etat = 20
GROUP BY mois ORDER BY mois ASC;

--YEAR
SELECT  EXTRACT(YEAR FROM date_publication) AS annee,  COUNT(*) AS nombre_annonces
FROM "public".annonce WHERE annonce.etat = 20
GROUP BY EXTRACT(YEAR FROM date_publication)
ORDER BY EXTRACT(YEAR FROM date_publication) ASC;

--Nombre d'inscrit par mois, par ans(graphe)
--BOTH
SELECT EXTRACT(YEAR FROM date_inscription) AS annee, EXTRACT(MONTH FROM date_inscription) AS mois, COUNT(*) AS nombre_inscrits FROM utilisateur
GROUP BY EXTRACT(YEAR FROM date_inscription), EXTRACT(MONTH FROM date_inscription)  ORDER BY annee, mois;

--MONTH
SELECT EXTRACT(YEAR FROM date_inscription) AS annee, COUNT(*) AS nombre_inscrits
FROM utilisateur
GROUP BY EXTRACT(YEAR FROM date_inscription)  ORDER BY annee;

--YEAR
SELECT gs.mois, COALESCE(COUNT(u.id), 0) AS nombre_inscrits
FROM generate_series(1, 12) gs(mois)
         LEFT JOIN utilisateur u ON EXTRACT(MONTH FROM u.date_inscription) = gs.mois
GROUP BY gs.mois  ORDER BY gs.mois;



--Nombre d'inscrit total
SELECT count(*) AS inscrit from utilisateur;

