package bg.jug.microprofile.hol.content;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ArticleResource {

    @Inject
    private ArticleRepository articleRepository;

    @GET
    @Path("/all")
    public Response getAllArticles() {
        JsonArray articlesArray = articleRepository.getAll()
                .stream()
                .map(Article::toJson)
                .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
        return Response.ok(articlesArray).build();
    }

    @GET
    @Path("/findById/{id}")
    public Response findArticleById(@PathParam("id") Long id) {
        return articleRepository.findById(id)
                .map(this::getFullArticleJson)
                .map(json -> Response.ok(json).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private JsonObject getFullArticleJson(Article article) {
        String authorEmail = article.getAuthor();
        // TODO request that from authors microservice
        JsonObject authorJson = Json.createObjectBuilder().build();
        return article.toJson(authorJson);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addArticle(JsonObject newArticle) {
        Article user = Article.fromJson(newArticle);
        articleRepository.createOrUpdate(user);
        return Response.ok().build();
    }

}
