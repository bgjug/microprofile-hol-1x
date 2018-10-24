package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.opentracing.Traced;

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

    @Inject
    private AuthorClient authorClient;

    @GET
    @Path("/all")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "No articles found",
                            content = @Content(mediaType = "text/plain")),
                    @APIResponse(
                            responseCode = "200",
                            description = "Returns all articles.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Article.class))) })
    @Operation(
            summary = "Returns an article by id.",
            description = "Returns an article by id.")
    @Traced(value = true, operationName = "ArticleResource.all")
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
    @Bulkhead(5)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "No article found",
                            content = @Content(mediaType = "text/plain")),
                    @APIResponse(
                            responseCode = "200",
                            description = "Returns the requested article.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Article.class))) })
    @Operation(
            summary = "Returns an article by id.",
            description = "Returns an article by id.")
    @Traced(value = true, operationName = "ArticleResource.byid")
    public Response findArticleById(@PathParam("id") Long id) {
        return articleRepository.findById(id)
                .map(this::getFullArticleJson)
                .map(json -> Response.ok(json).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private JsonObject getFullArticleJson(Article article) {
        String authorEmail = article.getAuthor();
        JsonObject authorJson = authorClient.findAuthorByEmail(authorEmail);
        return article.toJson(authorJson);
    }

//    @Inject
//    @Claim("groups")
//    private Set<String> roles;

    @Inject
    @Claim("given_name")
    private String givenName;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Add an article.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Article.class))) })
    @Operation(
            summary = "Add an article.",
            description = "Add an article.")
    @Traced(value = true, operationName = "ArticleResource.add")
    public Response addArticle(JsonObject newArticle) {
//        if (!roles.contains("author")) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }

        System.out.println("Given name: " + givenName);

        Article user = Article.fromJson(newArticle);
        articleRepository.createOrUpdate(user);
        return Response.ok().build();
    }

}
