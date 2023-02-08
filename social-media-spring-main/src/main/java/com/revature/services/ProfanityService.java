package com.revature.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class ProfanityService {
    private HashMap<String,Boolean> profanity;
    private ArrayList<String> profanityList;
    private File profanityConfig;
    public ProfanityService()
    {
        try{
            //profanityConfig = new File("src/main/resources/profanityList.txt");
            //BufferedReader reader = new BufferedReader(new FileReader(profanityConfig));
            profanity = new HashMap<String,Boolean>();
            profanityList = new ArrayList<>();
/*
            String current = "";
            while (true)
            {
                current = reader.readLine();
                if (current==null)
                    break;
                profanity.put(current,true);
            }

 */


            String[] current = {"a55","a55hole","aeolus","ahole","anal","analprobe","anilingus","anus","areola","areole","arian","aryan","ass","assbang",
            "assbanged","assbangs","asses","assfuck","assfucker","assh0le","asshat","assho1e","ass hole","asshole","assholes","assmaster","assmunch","asswipe",
            "asswipes","azazel","azz","b1tch","babe","babes","ballsack","bang","banger","barf","bastard","bastards","bawdy","beaner","beardedclam","beastiality",
            "beatch","beater","beaver","beer","beeyotch","beotch","biatch","bigtits","big tits","bimbo","bitch","bitched","bitches","bitchy","blow job","blow","blowjob",
            "blowjobs","bod","bodily","boink","bollock","bollocks","bollok","bone","boned","boner","boners","bong","boob","boobies","boobs","booby","booger","bookie",
            "bootee","bootie","booty","booze","boozer","boozy","bosom","bosomy","bowel","bowels","bra","brassiere","breast","breasts","bugger","bukkake","bullshit",
            "bull shit","bullshits","bullshitted","bullturds","bung","busty","butt","butt fuck","buttfuck","buttfucker","buttfucker","buttplug","c.0.c.k","c.o.c.k.",
            "c.u.n.t","c0ck","c-0-c-k","caca","cahone","cameltoe","carpetmuncher","cawk","cervix","chinc","chincs","chink","chink","chode","chodes","cl1t","climax",
            "clit","clitoris","clitorus","clits","clitty","cocain","cocaine","cock","c-o-c-k","cockblock","cockholster","cockknocker","cocks","cocksmoker","cocksucker",
            "cock sucker","coital","commie","condom","coon","coons","corksucker","crabs","crack","cracker","crackwhore","crap","crappy","cum","cummin","cumming","cumshot",
            "cumshots","cumslut","cumstain","cunilingus","cunnilingus","cunny","cunt","cunt","c-u-n-t","cuntface","cunthunter","cuntlick","cuntlicker","cunts","d0ng",
            "d0uch3","d0uche","d1ck","d1ld0","d1ldo","dago","dagos","dammit","damn","damned","damnit","dawgie-style","dick","dickbag","dickdipper","dickface","dickflipper",
            "dickhead","dickheads","dickish","dick-ish","dickripper","dicksipper","dickweed","dickwhipper","dickzipper","diddle","dike","dildo","dildos","diligaf",
            "dillweed","dimwit","dingle","dipship","doggie-style","doggy-style","dong","doofus","doosh","dopey","douch3","douche","douchebag","douchebags","douchey",
            "drunk","dumass","dumbass","dumbasses","dummy","dyke","dykes","ejaculate","enlargement","erect","erection","erotic","essohbee","extacy","extasy",
            "f.u.c.k","fack","fag","fagg","fagged","faggit","faggot","fagot","fags","faig","faigt","fannybandit","fart","fartknocker","fat","felch","felcher",
            "felching","fellate","fellatio","feltch","feltcher","fisted","fisting","fisty","floozy","foad","fondle","foobar","foreskin","freex","frigg","frigga",
            "fubar","fuck","f-u-c-k","fuckass","fucked","fucked","fucker","fuckface","fuckin","fucking","fucknugget","fucknut","fuckoff","fucks","fucktard",
            "fuck-tard","fuckup","fuckwad","fuckwit","fudgepacker","fuk","fvck","fxck","gae","gai","ganja","gay","gays","gey","gfy","ghay","ghey","gigolo",
            "glans","goatse","godamn","godamnit","goddam","goddammit","goddamn","goldenshower","gonad","gonads","gook","gooks","gringo","gspot","g-spot",
            "gtfo","guido","h0m0","h0mo","handjob","hard on","he11","hebe","heeb","hell","hemp","heroin","herp","herpes","herpy","hitler","hiv","hobag",
            "hom0","homey","homo","homoey","honky","hooch","hookah","hooker","hoor","hootch","hooter","hooters","horny","hump","humped","humping","hussy",
            "hymen","inbred","incest","injun","j3rk0ff","jackass","jackhole","jackoff","jap","japs","jerk","jerk0ff","jerked","jerkoff","jism","jiz","jizm",
            "jizz","jizzed","junkie","junky","kike","kikes","kill","kinky","kkk","klan","knobend","kooch","kooches","kootch","kraut","kyke","labia","lech","leper",
            "lesbians","lesbo","lesbos","lez","lezbian","lezbians","lezbo","lezbos","lezzie","lezzies","lezzy","lmao","lmfao","loin","loins","lube","lusty","mams",
            "massa","masterbate","masterbating","masterbation","masturbate","masturbating","masturbation","maxi","menses","menstruate","menstruation","meth",
            "m-fucking","mofo","molest","moolie","moron","motherfucka","motherfucker","motherfucking","mtherfucker","mthrfucker","mthrfucking","muff","muffdiver",
            "murder","muthafuckaz","muthafucker","mutherfucker","mutherfucking","muthrfucking","nad","nads","naked","napalm","nappy","nazi","nazism","negro","nigga"
            ,"niggah","niggas","niggaz","nigger","nigger","niggers","niggle","niglet","nimrod","ninny","nipple","nooky","nympho","opiate","opium","oral","orally",
            "organ","orgasm","orgasmic","orgies","orgy","ovary","ovum","ovums","p.u.s.s.y.","paddy","paki","pantie","panties","panty","pastie","pasty","pcp","pecker",
            "pedo","pedophile","pedophilia","pedophiliac","pee","peepee","penetrate","penetration","penial","penile","penis","perversion","peyote","phalli","phallic","phuck","pillowbiter","pimp","pinko","piss","pissed","pissoff","piss-off","pms","polack","pollock","poon","poontang","porn","porno","pornography","pot","potty","prick","prig","prostitute","prude","pube","pubic","pubis","punkass","punky","puss","pussies","pussy","pussypounder","puto","queaf","queef","queef","queer","queero","queers","quicky","quim","racy","rape","raped","raper","rapist","raunch","rectal","rectum","rectus","reefer","reetard","reich","retard","retarded","revue","rimjob","ritard","rtard","r-tard","rum","rump","rumprammer","ruski","s.h.i.t.","s.o.b.","s0b","sadism","sadist","scag","scantily","schizo","schlong","screw","screwed","scrog","scrot","scrote","scrotum","scrud","scum","seaman","seamen","seduce","semen","sex","sexual","sh1t","s-h-1-t","shamedame","shit","s-h-i-t","shite","shiteater","shitface","shithead","shithole","shithouse","shits","shitt","shitted","shitter","shitty","shiz","sissy","skag","skank","slave","sleaze","sleazy","slut","slutdumper","slutkiss","sluts","smegma","smut","smutty","snatch","sniper","snuff","s-o-b","sodom","souse","soused","sperm","spic","spick","spik","spiks","spooge","spunk","steamy","stfu","stiffy","stoned","strip","stroke","stupid","suck","sucked","sucking","sumofabiatch","t1t","tampon","tard","tawdry","teabagging","teat","terd","teste","testee","testes","testicle","testis","thrust","thug","tinkle","tit","titfuck","titi","tits","tittiefucker","titties","titty","tittyfuck","tittyfucker","toke","toots","tramp","transsexual","trashy","tubgirl","turd","tush","twat","twats","ugly","undies","unwed","urinal","urine","uterus","uzi","vag","vagina","valium","viagra","virgin","vixen","vodka","vomit","voyeur","vulgar","vulva","wad","wang","wank","wanker","wazoo","wedgie","weed","weenie","weewee","weiner","weirdo","wench","wetback","wh0re","wh0reface","whitey","whiz","whoralicious","whore","whorealicious","whored","whoreface","whorehopper","whorehouse","whores","whoring","wigger","womb","woody","wop","wtf","x-rated","xxx","yeasty","yobbo","zoophile","abbo","abo","abortion","abuse","addict",
            "addicts","adult","africa","african","alla","allah","alligatorbait","amateur","american","anal","analannie","analsex","angie","angry","anus","arab","arabs","areola","argie","aroused","arse","arsehole","asian","ass","assassin","assassinate","assassination","assault","assbagger","assblaster","assclown","asscowboy","asses","assfuck","assfucker","asshat","asshole","assholes","asshore","assjockey","asskiss","asskisser","assklown","asslick","asslicker","asslover","assman","assmonkey","assmunch","assmuncher","asspacker","asspirate","asspuppies","assranger","asswhore","asswipe","athletesfoot","attack","australian","babe","babies","backdoor","backdoorman","backseat","badfuck","balllicker","balls","ballsack","banging","baptist","barelylegal","barf","barface","barfface","bast","bastard","bazongas","bazooms","beaner","beast","beastality","beastial","beastiality","beatoff","beat-off","beatyourmeat","beaver","bestial","bestiality","bi","biatch","bible","bicurious","bigass","bigbastard","bigbutt","bigger","bisexual","bi-sexual","bitch","bitcher","bitches","bitchez","bitchin","bitching","bitchslap","bitchy","biteme","black","blackman","blackout","blacks","blind","blow","blowjob","boang","bogan","bohunk","bollick","bollock","bomb","bombers","bombing","bombs","bomd","bondage","boner","bong","boob","boobies","boobs","booby","boody","boom","boong","boonga","boonie","booty","bootycall","bountybar","bra","brea5t","breast","breastjob","breastlover","breastman","brothel","bugger","buggered","buggery","bullcrap","bulldike","bulldyke","bullshit","bumblefuck","bumfuck","bunga","bunghole","buried","burn","butchbabes","butchdike","butchdyke","butt","buttbang","butt-bang","buttface","buttfuck","butt-fuck","buttfucker","butt-fucker","buttfuckers","butt-fuckers","butthead","buttman","buttmunch","buttmuncher","buttpirate","buttplug","buttstain","byatch","cacker","cameljockey","cameltoe","canadian","cancer",
            "carpetmuncher","carruth","catholic","catholics","cemetery","chav","cherrypopper","chickslick","children's","chin","chinaman","chinamen","chinese","chink","chinky","choad","chode","christ","christian","church","cigarette","cigs","clamdigger","clamdiver","clit","clitoris","clogwog","cocaine","cock","cockblock","cockblocker","cockcowboy","cockfight","cockhead","cockknob","cocklicker","cocklover","cocknob","cockqueen","cockrider","cocksman","cocksmith","cocksmoker","cocksucer","cocksuck","cocksucked","cocksucker","cocksucking","cocktail","cocktease","cocky","cohee","coitus","color","colored","coloured","commie","communist","condom","conservative","conspiracy","coolie","cooly","coon","coondog","copulate","cornhole","corruption","cra5h","crabs","crack","crackpipe","crackwhore","crack-whore","crap","crapola","crapper","crappy","crash","creamy","crime","crimes","criminal","criminals","crotch","crotchjockey","crotchmonkey","crotchrot","cum","cumbubble","cumfest","cumjockey","cumm","cummer","cumming","cumquat","cumqueen","cumshot","cunilingus","cunillingus","cunn","cunnilingus","cunntt","cunt","cunteyed","cuntfuck","cuntfucker","cuntlick","cuntlicker","cuntlicking","cuntsucker","cybersex","cyberslimer","dago","dahmer","dammit","damn","damnation","damnit","darkie","darky","datnigga","dead","deapthroat","death","deepthroat","defecate","dego","demon","deposit","desire","destroy","deth","devil","devilworshipper","dick","dickbrain","dickforbrains","dickhead","dickless","dicklick","dicklicker","dickman","dickwad","dickweed","diddle","die","died","dies","dike","dildo","dingleberry","dink","dipshit","dipstick","dirty","disease","diseases","disturbed","dive","dix","dixiedike","dixiedyke","doggiestyle","doggystyle","dong","doodoo","doo-doo","doom","dope","dragqueen","dragqween","dripdick","drug","drunk","drunken","dumb","dumbass","dumbbitch","dumbfuck","dyefly","dyke","easyslut","eatballs","eatme","eatpussy","ecstacy","ejaculate","ejaculated","ejaculating","ejaculation","enema","enemy","erect","erection","ero","escort","ethiopian","ethnic","european","evl","excrement","execute","executed","execution","executioner","explosion","facefucker","faeces","fag","fagging","faggot","fagot","failed","failure","fairies","fairy","faith","fannyfucker","fart","farted","farting","farty","fastfuck","fat","fatah","fatass","fatfuck","fatfucker","fatso","fckcum","fear","feces","felatio","felch","felcher","felching","fellatio","feltch","feltcher","feltching","fetish","fight","filipina","filipino","fingerfood","fingerfuck","fingerfucked","fingerfucker","fingerfuckers","fingerfucking","fire","firing","fister","fistfuck","fistfucked","fistfucker","fistfucking","fisting","flange","flasher","flatulence","floo","flydie","flydye","fok","fondle","footaction","footfuck","footfucker","footlicker","footstar","fore","foreskin","forni","fornicate","foursome","fourtwenty","fraud","freakfuck","freakyfucker","freefuck","fu","fubar","fuc","fucck","fuck","fucka","fuckable","fuckbag","fuckbuddy",
            "fucked","fuckedup","fucker","fuckers","fuckface","fuckfest","fuckfreak","fuckfriend","fuckhead","fuckher","fuckin","fuckina","fucking","fuckingbitch","fuckinnuts","fuckinright","fuckit","fuckknob","fuckme","fuckmehard","fuckmonkey","fuckoff","fuckpig","fucks","fucktard","fuckwhore","fuckyou","fudgepacker","fugly","fuk","fuks","funeral","funfuck","fungus","fuuck","gangbang","gangbanged","gangbanger","gangsta","gatorbait","gay","gaymuthafuckinwhore","gaysex","geez","geezer","geni","genital","german","getiton","gin","ginzo","gipp","girls","givehead","glazeddonut","gob","god","godammit","goddamit","goddammit","goddamn","goddamned","goddamnes","goddamnit","goddamnmuthafucker","goldenshower","gonorrehea","gonzagas","gook","gotohell","goy","goyim","greaseball","gringo","groe","gross","grostulation","gubba","gummer","gun","gyp","gypo","gypp","gyppie","gyppo","gyppy","hamas","handjob","hapa","harder","hardon","harem","headfuck","headlights","hebe","heeb","hell","henhouse","heroin","herpes","heterosexual","hijack","hijacker","hijacking","hillbillies","hindoo","hiscock","hitler","hitlerism","hitlerist","hiv","ho","hobo","hodgie","hoes","hole","holestuffer","homicide","homo","homobangers","homosexual","honger","honk","honkers","honkey","honky","hook","hooker","hookers","hooters","hore","hork","horn","horney","horniest","horny","horseshit","hosejob","hoser","hostage","hotdamn","hotpussy","hottotrot","hummer","husky","hussy","hustler","hymen","hymie","iblowu","idiot","ikey","illegal","incest","insest","intercourse","interracial","intheass","inthebuff","israel","israeli","israel's","italiano","itch","jackass","jackoff","jackshit","jacktheripper","jade","jap","japanese","japcrap","jebus","jeez","jerkoff","jesus","jesuschrist","jew","jewish","jiga","jigaboo","jigg","jigga","jiggabo","jigger","jiggy","jihad","jijjiboo","jimfish","jism","jiz","jizim","jizjuice","jizm","jizz","jizzim","jizzum","joint","juggalo","jugs","junglebunny","kaffer","kaffir","kaffre","kafir","kanake","kid","kigger","kike","kill","killed","killer","killing","kills","kink","kinky","kissass","kkk","knife","knockers","kock","kondum","koon","kotex","krap","krappy","kraut","kum","kumbubble","kumbullbe","kummer","kumming","kumquat","kums","kunilingus","kunnilingus","kunt","ky","kyke","lactate","laid","lapdance","latin","lesbain","lesbayn","lesbian","lesbin","lesbo","lez","lezbe","lezbefriends","lezbo","lezz","lezzo","liberal","libido","licker","lickme","lies","limey","limpdick","limy","lingerie","liquor","livesex","loadedgun","lolita","looser","loser","lotion","lovebone","lovegoo","lovegun","lovejuice","lovemuscle","lovepistol","loverocket","lowlife","lsd","lubejob","lucifer","luckycammeltoe","lugan","lynch","macaca","mad","mafia","magicwand","mams","manhater","manpaste","marijuana","mastabate","mastabater","masterbate","masterblaster","mastrabator","masturbate","masturbating","mattressprincess","meatbeatter","meatrack","meth","mexican","mgger","mggor","mickeyfinn","mideast","milf","minority","mockey","mockie","mocky","mofo","moky","moles","molest","molestation","molester","molestor","moneyshot","mooncricket","mormon","moron","moslem","mosshead","mothafuck","mothafucka","mothafuckaz","mothafucked","mothafucker","mothafuckin","mothafucking","mothafuckings","motherfuck","motherfucked","motherfucker","motherfuckin","motherfucking","motherfuckings","motherlovebone","muff","muffdive","muffdiver","muffindiver","mufflikcer","mulatto","muncher","munt","murder","murderer","muslim","naked","narcotic","nasty","nastybitch","nastyho","nastyslut","nastywhore","nazi","necro","negro","negroes","negroid","negro's","nig","niger","nigerian","nigerians","nigg","nigga","niggah","niggaracci","niggard","niggarded","niggarding","niggardliness","niggardliness's","niggardly","niggards","niggard's","niggaz","nigger","niggerhead","niggerhole","niggers","nigger's","niggle","niggled","niggles","niggling","nigglings","niggor","niggur","niglet","nignog","nigr","nigra","nigre","nip","nipple","nipplering","nittit","nlgger","nlggor","nofuckingway","nook","nookey","nookie","noonan","nooner","nude","nudger","nuke","nutfucker","nymph","ontherag","oral","orga","orgasim","orgasm","orgies","orgy","osama","paki","palesimian","palestinian","pansies","pansy","panti","panties",
            "payo","pearlnecklace","peck","pecker","peckerwood","pee","peehole","pee-pee","peepshow","peepshpw","pendy","penetration","peni5","penile","penis","penises","penthouse","period","perv","phonesex","phuk","phuked","phuking","phukked","phukking","phungky","phuq","pi55","picaninny","piccaninny","pickaninny","piker","pikey","piky","pimp","pimped","pimper","pimpjuic","pimpjuice","pimpsimp","pindick","piss","pissed","pisser","pisses","pisshead","pissin","pissing","pissoff","pistol","pixie","pixy","playboy","playgirl","pocha","pocho","pocketpool","pohm","polack","pom","pommie","pommy","poo","poon","poontang","poop","pooper","pooperscooper","pooping","poorwhitetrash","popimp","porchmonkey","porn","pornflick","pornking","porno","pornography","pornprincess","pot","poverty","premature","pric","prick","prickhead","primetime","propaganda","pros","prostitute","protestant","pu55i","pu55y","pube","pubic","pubiclice","pud","pudboy","pudd","puddboy","puke","puntang","purinapricness","puss","pussie","pussies","pussy","pussycat","pussyeater","pussyfucker","pussylicker","pussylips","pussylover","pussypounder","pusy","quashie","queef","queer","quickie","quim","ra8s","rabbi","racial","racist","radical","radicals","raghead","randy","rape","raped","raper","rapist","rearend","rearentry","rectum","redlight","redneck","reefer","reestie","refugee","reject","remains","rentafuck","republican","rere","retard","retarded","ribbed","rigger","rimjob","rimming","roach","robber","roundeye","rump","russki","russkie","sadis","sadom","samckdaddy","sandm","sandnigger","satan","scag","scallywag","scat","schlong","screw","screwyou","scrotum","scum","semen","seppo","servant","sex","sexed","sexfarm","sexhound","sexhouse","sexing","sexkitten","sexpot","sexslave","sextogo","sextoy","sextoys","sexual","sexually","sexwhore","sexy","sexymoma","sexy-slim","shag","shaggin","shagging","shat","shav","shawtypimp","sheeney","shhit","shinola","shit","shitcan","shitdick","shite","shiteater","shited","shitface","shitfaced","shitfit","shitforbrains","shitfuck","shitfucker","shitfull","shithapens","shithappens","shithead","shithouse","shiting","shitlist","shitola","shitoutofluck","shits","shitstain","shitted","shitter","shitting","shitty","shoot","shooting","shortfuck","showtime","sick","sissy","sixsixsix","sixtynine","sixtyniner","skank","skankbitch","skankfuck","skankwhore","skanky","skankybitch","skankywhore","skinflute","skum","skumbag","slant","slanteye","slapper","slaughter","slav","slave","slavedriver","sleezebag","sleezeball","slideitin","slime","slimeball","slimebucket","slopehead","slopey","slopy","slut","sluts","slutt","slutting","slutty","slutwear","slutwhore","smack","smackthemonkey","smut","snatch","snatchpatch","snigger","sniggered","sniggering","sniggers","snigger's","sniper","snot","snowback","snownigger","sob","sodom","sodomise","sodomite","sodomize","sodomy","sonofabitch","sonofbitch","sooty","sos","soviet","spaghettibender","spaghettinigger","spank","spankthemonkey","sperm","spermacide","spermbag","spermhearder","spermherder","spic","spick","spig","spigotty","spik","spit","spitter","splittail","spooge","spreadeagle","spunk","spunky","squaw","stagg","stiffy","strapon","stringer","stripclub","stroke","stroking","stupid","stupidfuck","stupidfucker","suck","suckdick","sucker","suckme","suckmyass","suckmydick","suckmytit","suckoff","suicide","swallow","swallower","swalow","swastika","sweetness","syphilis","taboo","taff","tampon","tang","tantra","tarbaby","tard","teat","terror","terrorist","teste","testicle","testicles","thicklips","thirdeye","thirdleg","threesome","threeway","timbernigger","tinkle","tit","titbitnipply","titfuck","titfucker","titfuckin","titjob","titlicker","titlover","tits","tittie","titties","titty","tnt","toilet","tongethruster","tongue","tonguethrust","tonguetramp","tortur","torture","tosser","towelhead","trailertrash","tramp","trannie","tranny","transexual","transsexual","transvestite","triplex","trisexual","trojan","trots","tuckahoe","tunneloflove","turd","turnon","twat","twink","twinkie","twobitwhore","uck","uk","unfuckable","upskirt","uptheass","upthebutt","urinary","urinate","urine","usama","uterus","vagina","vaginal","vatican","vibr","vibrater","vibrator","vietcong","violence","virgin","virginbreaker","vomit","vulva","wab","wank","wanker","wanking","waysted","weapon","weenie","weewee","welcher","welfare","wetb","wetback","wetspot","whacker","whash","whigger","whiskey","whiskeydick","whiskydick","whit","whitenigger","whites","whitetrash","whitey","whiz","whop","whore","whorefucker","whorehouse","wigger","willie","williewanker","willy","wn","wog","women's","wop","wtf","wuss","wuzzie","xtc","xxx","yankee","yellowman","zigabo","zipperhead","javascript"};
            for (String s:current){
                profanity.put(s,true);
            }


            profanity.forEach((word,value)->
                profanityList.add(word)
            );//value is always true.... so we're using this to map the hashmap to an arrayList for searching for 'lookalikes'.
            //reader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String filterProfanity(String text) {
        String[] words = text.split(" ");
        StringBuilder returnedString= new StringBuilder();
        for (int i = 0; i<words.length; i++)
        {
           if(profanity.get(words[i].toLowerCase())!=null||profanityLikely(words[i]))
               words[i] = asterisks(words[i].length());
           returnedString.append(words[i]);
           if(i+1!=words.length)
               returnedString.append(" ");

        }
        return returnedString.toString();
    }

    public boolean profanityLikely(String s)
    {
        String temp = Normalizer.normalize(s.strip().toLowerCase(),Normalizer.Form.NFD);//strip white space, standardize spelling.
        temp = temp.replaceAll("\\p{M}}", ""); //removes any remaining "funny" characters.
        if(profanity.get(temp)!=null)
            return true;
        temp = temp.replaceAll("[0-9]","");//remove numbers.
        if(temp.length()==0)
            return false;
        if(profanity.get(temp)!=null)//if the phrase without numbers is a match.
            return true;
        if(profanity.get(temp.toLowerCase())!=null) //pretty extensively checked by this point, but the final check sees if there's multiple 'contains'. will likely result in overcropping.
            return true;

        int count = 0;
        for(int i = 0; i<profanityList.size();i++)
        {
            if (temp.contains(profanityList.get(i)))
                count++;
        }
        return count>2;
    }

    private String asterisks(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}