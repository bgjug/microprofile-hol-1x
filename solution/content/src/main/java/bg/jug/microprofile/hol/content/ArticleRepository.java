package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.metrics.MetricRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@ApplicationScoped
public class ArticleRepository {

    private Map<Long, Article> articles = new ConcurrentHashMap<>();

    @Inject
    private MetricRegistry metricRegistry;

    @PostConstruct
    public void initArticles() {
        Article article1 = new Article(null, "Troubleless", "He was not the Model Boy of the village. He knew the model boy very well though—and loathed him.<p>" +
                "Within two minutes, or even less, he had forgotten all his troubles. Not because his troubles were one whit less heavy and bitter to him than a man's are to a man, but because a new and powerful interest bore them down and drove them out of his mind for the time—just as men's misfortunes are forgotten in the excitement of new enterprises. This new interest was a valued novelty in whistling, which he had just acquired from a negro, and he was suffering to practise it un-disturbed. It consisted in a peculiar bird-like turn, a sort of liquid warble, produced by touching the tongue to the roof of the mouth at short intervals in the midst of the music—the reader probably remembers how to do it, if he has ever been a boy. Diligence and attention soon gave him the knack of it, and he strode down the street with his mouth full of harmony and his soul full of gratitude. He felt much as an astronomer feels who has discovered a new planet—no doubt, as far as strong, deep, unalloyed pleasure is concerned, the advantage was with the boy, not the astronomer.<p>" +
                "The summer evenings were long. It was not dark, yet. Presently Tom checked his whistle. A stranger was before him—a boy a shade larger than himself. A new-comer of any age or either sex was an im-pressive curiosity in the poor little shabby village of St. Petersburg. This boy was well dressed, too—well dressed on a week-day. This was simply astounding. His cap was a dainty thing, his close-buttoned blue cloth roundabout was new and natty, and so were his pantaloons. He had shoes on—and it was only Friday. He even wore a necktie, a bright bit of ribbon. He had a citified air about him that ate into Tom's vitals. The more Tom stared at the splendid marvel, the higher he turned up his nose at his finery and the shabbier and shabbier his own outfit seemed to him to grow.", "frodo@example.org");
        Article article2 = new Article(null, "Down the Rabbit-Hole", "There was nothing so very remarkable in that; nor did Alice think it so very much out of the way to hear the Rabbit say to itself, ‘Oh dear! Oh dear! I shall be late!’ (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually took a watch out of its waistcoat-pocket, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.<p>" +
                "In another moment down went Alice after it, never once considering how in the world she was to get out again.<p>" +
                "The rabbit-hole went straight on like a tunnel for some way, and then dipped suddenly down, so suddenly that Alice had not a moment to think about stopping herself before she found herself falling down a very deep well.", "frodo@example.org");
        Article article3 = new Article(null, "Full swing", "Anna Pávlovna’s reception was in full swing. The spindles hummed steadily and ceaselessly on all sides. With the exception of the aunt, beside whom sat only one elderly lady, who with her thin careworn face was rather out of place in this brilliant society, the whole company had settled into three groups. One, chiefly masculine, had formed round the abbé. Another, of young people, was grouped round the beautiful Princess Hélène, Prince Vasíli’s daughter, and the little Princess Bolkónskaya, very pretty and rosy, though rather too plump for her age. The third group was gathered round Mortemart and Anna Pávlovna.<p>" +
                "The vicomte was a nice-looking young man with soft features and polished manners, who evidently considered himself a celebrity but out of politeness modestly placed himself at the disposal of the circle in which he found himself. Anna Pávlovna was obviously serving him up as a treat to her guests. As a clever maître d’hôtel serves up as a specially choice delicacy a piece of meat that no one who had seen it in the kitchen would have cared to eat, so Anna Pávlovna served up to her guests, first the vicomte and then the abbé, as peculiarly choice morsels. The group about Mortemart immediately began discussing the murder of the Duc d’Enghien. The vicomte said that the Duc d’Enghien had perished by his own magnanimity, and that there were particular reasons for Buonaparte’s hatred of him.", "gandalf@example.org");
        Article article4 = new Article(null, "All art is quite useless", "The studio was filled with the rich odour of roses, and when the light summer wind stirred amidst the trees of the garden, there came through the open door the heavy scent of the lilac, or the more delicate perfume of the pink-flowering thorn.<p>" +
                "From the corner of the divan of Persian saddle-bags on which he was lying, smoking, as was his custom, innumerable cigarettes, Lord Henry Wotton could just catch the gleam of the honey-sweet and honey-coloured blossoms of a laburnum, whose tremulous branches seemed hardly able to bear the burden of a beauty so flamelike as theirs; and now and then the fantastic shadows of birds in flight flitted across the long tussore-silk curtains that were stretched in front of the huge window, producing a kind of momentary Japanese effect, and making him think of those pallid, jade-faced painters of Tokyo who, through the medium of an art that is necessarily immobile, seek to convey the sense of swiftness and motion. The sullen murmur of the bees shouldering their way through the long unmown grass, or circling with monotonous insistence round the dusty gilt horns of the straggling woodbine, seemed to make the stillness more oppressive. The dim roar of London was like the bourdon note of a distant organ.", "frodo@example.org");
        Article article5 = new Article(null, "Yo-ho-ho", "QUIRE TRELAWNEY, Dr. Livesey, and the rest of these gentlemen having asked me to write down the whole particulars about Treasure Island, from the beginning to the end, keeping nothing back but the bearings of the island, and that only because there is still treasure not yet lifted, I take up my pen in the year of grace 17__ and go back to the time when my father kept the Admiral Benbow inn and the brown old seaman with the sabre cut first took up his lodging under our roof.<p>" +
                "I remember him as if it were yesterday, as he came plodding to the inn door, his sea-chest following behind him in a hand-barrow—a tall, strong, heavy, nut-brown man, his tarry pigtail falling over the shoulder of his soiled blue coat, his hands ragged and scarred, with black, broken nails, and the sabre cut across one cheek, a dirty, livid white. I remember him looking round the cover and whistling to himself as he did so, and then breaking out in that old sea-song that he sang so often afterwards", "gandalf@example.org");
        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
        articles.put(article3.getId(), article3);
        articles.put(article4.getId(), article4);
        articles.put(article5.getId(), article5);



    }

    public List<Article> getAll() {
        return new ArrayList<>(articles.values());
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Asynchronous
    @Bulkhead(value = 5,
              waitingTaskQueue = 8)
    public Future<Void> createOrUpdate(Article article) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        articles.put(article.getId(), article);
        return CompletableFuture.completedFuture(null);
    }


}
