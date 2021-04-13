#!/usr/bin/env bash
set -eu

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
export CURL_OPTS='-s'

for i in {1..10}; do
    suffix="$(echo -n "${i}-$(date --iso-8601=ns)" | md5sum | base64 | sed -r -e 's/[^a-zA-Z]+//g' | head -c4)"
    suffix="${suffix,,}"
    title="Title - article title ${suffix} -"
    author="John ${suffix^} Doe"
    #body="Very interest article here. (${suffix})"
    body="
<p>
Very interesting article body will be here (${suffix}). Following paragraphs are from <i>``Alice's adventures Under Ground'' by Lewis Carroll</i>.
</p>
<p>
Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, and where is the use of a book, thought Alice, without pictures or conversations? So she was considering in her own mind, (as well as she could, for the hot day made her feel very sleepy and stupid,) whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a white rabbit with pink eyes ran close by her.
</p>
<p>
There was nothing so very remarkable in that; nor did Alice think it so very much out of the way to hear the rabbit say to itself, \"dear, dear! I shall be too late!\" (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the rabbit actually took a watch out of its waistcoat-pocket, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket or a watch to take out of it, and, full of curiosity, she ran across the field after it, and was just in time to see it pop down a large rabbit-hole under the hedge. In another moment down went Alice after it, never once considering how in the world she was to get out again.
</p>
<p>
The rabbit-hole went straight on like a tunnel for some way, and then dipped suddenly down, so suddenly, that Alice had not a moment to think about stopping herself, before she found herself falling down what seemed a deep well. Either the well was very deep, or she fell very slowly, for she had plenty of time as she went down to look about her, and to wonder what would happen next. First, she tried to look down and make out what she was coming to, but it was too dark to see anything: then, she looked at the sides of the well, and noticed that they were filled with cupboards and book-shelves; here and there were maps and pictures hung on pegs. She took a jar down off one of the shelves as she passed: it was labelled \"Orange Marmalade,\" but to her great disappointment it was empty: she did not like to drop the jar, for fear of killing somebody underneath, so managed to put it into one of the cupboards as she fell past it.
</p>
<p>
\"Well!\" thought Alice to herself, \"after such a fall as this, I shall think nothing of tumbling down stairs! How brave they'll all think me at home! Why, I wouldn't say anything about it, even if I fell off the top of the house!\" (which was most likely true.)
</p>
<p>
Down, down, down. Would the fall never come to an end? \"I wonder how many miles I've fallen by this time?\" said she aloud, \"I must be getting somewhere near the centre of the earth. Let me see: that would be four thousand miles down, I think—\" (for you see Alice had learnt several things of this sort in her lessons in the schoolroom, and though this was not a very good opportunity of showing off her knowledge, as there was no one to hear her, still it was good practice to say it over,) \"yes, that's the right distance, but then I wonder what Longitude or Latitude-line shall I be in?\" (Alice had no idea what Longitude was, or Latitude either, but she thought they were nice grand words to say.)
</p>
<p>
Presently she began again: \"I wonder if I shall fall right through the earth! How funny it'll be to come out among the people that walk with their heads downwards! But I shall have to ask them what the name of the country is, you know. Please, Ma'am, is this New Zealand or Australia?\"—and she tried to curtsey as she spoke, (fancy curtseying as you're falling through the air! do you think you could manage it?) \"and what an ignorant little girl she'll think me for asking! No, it'll never do to ask: perhaps I shall see it written up somewhere.\"
</p>
<p>
Down, down, down: there was nothing else to do, so Alice soon began talking again. \"Dinah will miss me very much tonight, I should think!\" (Dinah was the cat.) \"I hope they'll remember her saucer of milk at tea-time! Oh, dear Dinah, I wish I had you here! There are no mice in the air, I'm afraid, but you might catch a bat, and that's very like a mouse, you know, my dear. But do cats eat bats, I wonder?\" And here Alice began to get rather sleepy, and kept on saying to herself, in a dreamy sort of way, \"do cats eat bats? do cats eat bats?\" and sometimes, \"do bats eat cats?\" for, as she couldn't answer either question, it didn't much matter which way she put it. She felt that she was dozing off, and had just begun to dream that she was walking hand in hand with Dinah, and was saying to her very earnestly, \"Now, Dinah, my dear, tell me the truth. Did you ever eat a bat?\" when suddenly, bump! bump! down she came upon a heap of sticks and shavings, and the fall was over.
</p>
<p>
Alice was not a bit hurt, and jumped on to her feet directly: she looked up, but it was all dark overhead; before her was another long passage, and the white rabbit was still in sight, hurrying down it. There was not a moment to be lost: away went Alice like the wind, and just heard it say, as it turned a corner, \"my ears and whiskers, how late it's getting!\" She turned the corner after it, and instantly found herself in a long, low hall, lit up by a row of lamps which hung from the roof.
</p>
<p>
There were doors all round the hall, but they were all locked, and when Alice had been all round it, and tried them all, she walked sadly down the middle, wondering how she was ever to get out again: suddenly she came upon a little three-legged table, all made of solid glass; there was nothing lying upon it, but a tiny golden key, and Alice's first idea was that it might belong to one of the doors of the hall, but alas! either the locks were too large, or the key too small, but at any rate it would open none of them. However, on the second time round, she came to a low curtain, behind which was a door about eighteen inches high: she tried the little key in the keyhole, and it fitted! Alice opened the door, and looked down a small passage, not larger than a rat-hole, into the loveliest garden you ever saw. How she longed to get out of that dark hall, and wander about among those beds of bright flowers and those cool fountains, but she could not even get her head through the doorway, \"and even if my head would go through,\" thought poor Alice, \"it would be of very little use without my shoulders. Oh, how I wish I could shut up like a telescope! I think I could, if I only knew how to begin.\" For, you see, so many out-of-the-way things had happened lately, that Alice began to think that very few things indeed were really impossible.
</p>
<p>
There was nothing else to do, so she went back to the table, half hoping she might find another key on it, or at any rate a book of rules for shutting people up like telescopes: this time there was a little bottle on it—\"which certainly was not here before\" said Alice—and tied round the neck of the bottle was a paper label with the words DRINK ME beautifully printed on it in large letters.
</p>
<p>
It was all very well to say \"drink me,\" \"but I'll look first,\" said the wise little Alice, \"and see whether the bottle's marked \"poison\" or not,\" for Alice had read several nice little stories about children that got burnt, and eaten up by wild beasts, and other unpleasant things, because they would not remember the simple rules their friends had given them: such as, that, if you get into the fire, it will burn you, and that, if you cut your finger very deeply with a knife, it generally bleeds, and she had never forgotten that, if you drink a bottle marked \"poison,\" it is almost certain to disagree with you, sooner or later.
</p>
<p>
However, this bottle was not marked poison, so Alice tasted it, and finding it very nice, (it had, in fact, a sort of mixed flavour of cherry-tart, custard, pine-apple, roast turkey, toffee, and hot buttered toast,) she very soon finished it off.
</p>
<p>
\"What a curious feeling!\" said Alice, \"I must be shutting up like a telescope.\"
</p>
<p>
It was so indeed: she was now only ten inches high, and her face brightened up as it occurred to her that she was now the right size for going through the little door into that lovely garden. First, however, she waited for a few minutes to see whether she was going to shrink any further: she felt a little nervous about this, \"for it might end, you know,\" said Alice to herself, \"in my going out altogether, like a candle, and what should I be like then, I wonder?\" and she tried to fancy what the flame of a candle is like after the candle is blown out, for she could not remember ever having seen one. However, nothing more happened, so she decided on going into the garden at once, but, alas for poor Alice! when she got to the door, she found she had forgotten the little golden key, and when she went back to the table for the key, she found she could not possibly reach it: she could see it plainly enough through the glass, and she tried her best to climb up one of the legs of the table, but it was too slippery, and when she had tired herself out with trying, the poor little thing sat down and cried.
</p>
<p>
\"Come, there's no use in crying!\" said Alice to herself rather sharply, \"I advise you to leave off this minute!\" (she generally gave herself very good advice, and sometimes scolded herself so severely as to bring tears into her eyes, and once she remembered boxing her own ears for having been unkind to herself in a game of croquet she was playing with herself, for this curious child was very fond of pretending to be two people,) \"but it's no use now,\" thought poor Alice, \"to pretend to be two people! Why, there's hardly enough of me left to make one respectable person!\"
</p>
<p>
Soon her eye fell on a little ebony box that was lying under the table: she opened it, and found in it a very small cake, on which was lying a card with the words EAT ME beautifully printed on it in large letters. \"I'll eat,\" said Alice, \"and if it makes me larger, I can reach the key, and if it makes me smaller, I can creep under the door, so either way I'll get into the garden, and I don't care which happens!\"
</p>
<p>
She eat a little bit, and said anxiously to herself \"which way? which way?\" and laid her hand on the top of her head to feel which way it was growing, and was quite surprised to find that she remained the same size: to be sure this is what generally happens when one eats cake, but Alice had got into the way of expecting nothing but out-of-the way things to happen, and it seemed quite dull and stupid for life to go on in the common way.
</p>
<p>
So she set to work, and very soon finished off the cake.
</p>
"
    body="$(echo "${body}" | sed -r -e 's/"/\\"/g' -e 's/$/\\n/' | tr -d '\n')"
    echo "${body}"
    curl \
        ${CURL_OPTS} \
        -H 'Content-Type: application/json' \
        --data-binary '{"title":"'"${title}"'","author":"'"${author}"'","body":"'"${body}"'"}' \
        "${ARTICLE_URL}/api/articles/"
done
