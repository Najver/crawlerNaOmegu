package main;

import main.Client.Download;
import main.Dto.FlatDto;
import main.Files.Read;
import main.Files.Save;
import main.Parser.Parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final ExecutorService pool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        crawlLinks();
    }

    public static void gatherLinks() {
        for (int i = 1; i <= 1500; i++) {
            int finalI = i;
            pool.submit(() -> {
                try {
                    Save.saveToFile("estate_links.txt", Parser.parseLinks(Download.downloadWebpage("https://www.sreality.cz/hledani/prodej/byty/jihocesky-kraj,jihomoravsky-kraj,karlovarsky-kraj,kralovehradecky-kraj,liberecky-kraj,moravskoslezsky-kraj,olomoucky-kraj,pardubicky-kraj,plzensky-kraj,praha,stredocesky-kraj,ustecky-kraj,vysocina-kraj,zlinsky-kraj?strana=" + finalI + "&vlastnictvi=osobni")));
                    System.out.println("Saving... " + finalI);
                } catch (Exception e) {
                    pool.shutdown();
                    System.err.println(e);
                }
            });
        }
    }

    public static void crawlLinks() {
        try {
            String[] links = Read.readFileToString("estate_links.txt").split("\n");
            Save.saveToFile("estate_details.csv", "metraz,rozloha,energeticka_narocnost,stav,lokalita,cena,link\n");
            for (String link : links) {
                pool.submit(() -> {
                    try {
                        FlatDto f = Parser.parseFlatDto(Download.downloadWebpage(link));
                        Save.saveToFile("estate_details.csv", f + "," + link + "\n");
                        System.out.println(f);
                    } catch (Exception e) {
                        System.err.println("Failed to parse: " + link + " " + e);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
