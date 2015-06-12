package com.udl.softarch.springexample.controllers;

import com.udl.softarch.springexample.models.Song;
import com.udl.softarch.springexample.models.SongCollection;
import com.udl.softarch.springexample.repositories.SongCollectionRepository;
import com.udl.softarch.springexample.repositories.SongRepository;
import com.udl.softarch.springexample.services.SongCollectionSongService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.base.Preconditions;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xquery.*;
import java.io.*;
import java.lang.Object;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davidkaste on 16/02/15.
 */

@Controller
@RequestMapping("/songCollection/{idCollection}/")
public class SongController {

    @Autowired
    SongCollectionSongService songCollectionSongService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongCollectionRepository songCollectionRepository;

    @RequestMapping(value = "/songs/search", method = RequestMethod.GET)  //? get parameters from query?
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Iterable<Song> listArtistsBySong(@RequestParam(value = "song", required = true) String song
    ) throws MalformedURLException {
        ArrayList<String> bandList = new ArrayList<String>();
        String s = "https://musicbrainz.org/ws/2/recording/?query=";
        StringTokenizer st = new StringTokenizer(song);
        String aux = new String();
        while (st.hasMoreTokens()) {
            aux = aux + st.nextToken() + "%20";
        }
        s = s + aux;
        URL url = new URL(s);

        DocumentBuilderFactory doc = DocumentBuilderFactory.newInstance();
        /*try {
            DocumentBuilder db = doc.newDocumentBuilder();
            Document document = db.parse(url.openStream());
            document.getDocumentElement().normalize();
            NodeList recordingList = document.getElementsByTagName("recording");

            for (int temp = 0; temp < recordingList.getLength(); temp++) {
                Node nNode = recordingList.item(temp);
                Element e = (Element) nNode;
                NodeList artistList = e.getElementsByTagName("artist");
                Node artist = artistList.item(0);
                e = (Element) artist;
                NodeList nameList = e.getElementsByTagName("name");
                Node artistName = nameList.item(0);
                bandList.add(artistName.getTextContent().toString());
            }*/

        String albumsXQ =
                "declare namespace mmd=\"http://musicbrainz.org/ns/mmd-2.0#\";\n"
                        + "declare variable $doc external;\n"
                        + "for $rec in $doc//mmd:recording\n"
                        + "let $art := $rec/mmd:artist-credit\n"
                        + "let $rel := $rec//mmd:release[1]\n"
                        + "order by $rel/mmd:date\n"
                        + "return <song>\n"
                        + "<band>{$art//mmd:name/text()}</band>\n"
                        + "<album>{$rel/mmd:title/text()}</album>\n"
                        + "<releaseDate>{$rel/mmd:date/number()}</releaseDate>\n"
                        + "<releaseCountry>{$rel/mmd:country/text()}</releaseCountry>\n"
                        + "</song>";

        try {
            XQueryHelper xQueryHelper = new XQueryHelper(albumsXQ, url);
            ArrayList<XQueryHelper.Song> songs = xQueryHelper.getSongs();
            ArrayList<Song> songFinal = new ArrayList<Song>();
            for (XQueryHelper.Song song1 : songs)
                songFinal.add(new Song(song,song1.getBand(),song1.getAlbum(),song1.getReleaseCountry(),song1.getReleaseDate()));
            return songFinal;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }

    @RequestMapping(value = "/songs/search", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listArtistsBySongHTML(@RequestParam(value = "name", required = true) String song,
                                              @PathVariable("idCollection") Long idCol) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("searchList", listArtistsBySong(song));
        model.put("song", song);
        model.put("idCollection", idCol);
        return new ModelAndView("searchResult", "map", model);
    }

    // LIST
    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Song> list(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "10") int size,
                               @PathVariable("idCollection") Long id) {
        PageRequest request = new PageRequest(page, size);
        return songCollectionSongService.getSongsFromSongCollection(songCollectionRepository.findOne(id).getId());



    }

    @RequestMapping(value = "/songs", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHtml(@RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 @PathVariable("idCollection") Long idCol) {
        Map<String, Object> model = new HashMap<>();


        model.put("songs", list(page, size, idCol));
        model.put("idCollection", idCol);
        return new ModelAndView("allsongs", "map", model);
    }

    // obtener / retrieve
    @RequestMapping(value = "/songs/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Song retrieve(@PathVariable("id") Long id,
                         @PathVariable("idCollection") Long idColl) {

        Song s = songCollectionSongService.getSongFromSongCollection(id, idColl);
        Preconditions.checkNotNull(s, "Greeting with id %s not found", id);
        return s;
    }

    @RequestMapping(value = "/songs/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHTML(@PathVariable("id") Long id,
                                     @PathVariable("idCollection") Long idCol) {

        Map<String, Object> model = new HashMap<>();
        Song s = retrieve(id, idCol);
        model.put("song", retrieve(id, idCol));
        model.put("idCollection", idCol);
        return new ModelAndView("songview", "map", model);
    }

    // CREATE
    @RequestMapping(value = "/songs", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Song create(@Valid @RequestBody Song song,
                       @PathVariable("idCollection") Long idCol,
                       HttpServletResponse response) {
        //Song s = songRepository.save(song);
        songCollectionSongService.addSongToSongCollection(song,idCol);
        System.out.println("produces data");
        return song;
    }

    @RequestMapping(value = "/songs", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String createHtml(@Valid @ModelAttribute("song") Song song, BindingResult binding, HttpServletResponse response,
                             @PathVariable("idCollection") Long idCol) {
        System.out.println("produces html");
        if (binding.hasErrors()) {
            return "redirect:/songCollection/" + idCol + "/songs/form";
        }

        Long id = create(song, idCol,response).getId();
        return "redirect:/songCollection/" + idCol + "/songs/" + id;

        //return "heloo baby";
    }

    @RequestMapping(value = "/songs/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView createForm() {

        Song emptysong = new Song();
        return new ModelAndView("songform", "song", emptysong);
    }

    // UPDATE
    @RequestMapping(value = "/songs/{id}/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Song update(@PathVariable("id") Long id,
                       @Valid @RequestBody Song song,
                       @PathVariable("idCollection") Long idCol) {
        Song s = songRepository.findOne(id);
        Song s1 = songCollectionSongService.updateSongFromSongCollection(song, s.getId(), idCol);
        //Song s = songRepository.save(oldSong);
        return s1;
    }

    @RequestMapping(value = "/songs/{id}", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.OK)
    public String updateHTML(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("song") Song song,
                             @PathVariable("idCollection") Long idCol,
                             BindingResult binding) {

        if (binding.hasErrors()) {
            return "redirect:/songCollection/" + idCol + "/songs/" + id + "/form";
        }
        Song s = update(id, song, idCol);
        return "redirect:/songCollection/" + idCol + "/songs/" + s.getId().toString();
    }

    // Update form
    @RequestMapping(value = "/songs/{id}/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView updateForm(@PathVariable("id") Long id, @PathVariable("idCollection") Long idCol) {
        Map<String, Object> model = new HashMap<>();

        model.put("song", songCollectionSongService.getSongFromSongCollection(id, idCol));
        model.put("idCollection", idCol);

        return new ModelAndView("PutSongForm", "map", model);
    }

    // DELETE
    @RequestMapping(value = "/songs/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, @PathVariable("idCollection") Long idCol) {
        songCollectionSongService.deleteSongFromSongCollection(id, idCol);
        //songRepository.delete(songRepository.findOne(id));
    }

    @RequestMapping(value = "/songs/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHTML(@PathVariable("id") Long id, @PathVariable("idCollection") Long idCol) {
        delete(id, idCol);
        return "redirect:/songCollection/{id}/songs";
    }

    public static class XQueryHelper {
        private final Logger log = Logger.getLogger(XQueryHelper.class.getName());

        private XQPreparedExpression expr;
        private XQConnection conn;

        private JAXBContext jaxbContext;
        private Unmarshaller jaxbUnmarshaller;


        @XmlRootElement
        private static class Song {
            @XmlElement String band;
            @XmlElement String album;
            @XmlElement String releaseCountry;
            @XmlElement String releaseDate;

            public String getAlbum() {
                return album;
            }

            public String getReleaseCountry() {
                return releaseCountry;
            }

            public String getReleaseDate() {
                return releaseDate;
            }

            public String getBand() {
                return band;
            }

            @Override
            public String toString() {
                return band+"\n"+"Artist: "+album+"\n"+"Countries: "+releaseCountry+"\n"+"Year: "+releaseDate+"\n";
            }
        }

        XQueryHelper(String xquery, URL url)
                throws ClassNotFoundException, InstantiationException, IllegalAccessException, XQException, IOException, JAXBException {
            URLConnection urlconn = url.openConnection();
            urlconn.setReadTimeout(5000);

            XQDataSource xqds = (XQDataSource) Class.forName("net.sf.saxon.xqj.SaxonXQDataSource").newInstance();
            this.conn = xqds.getConnection();
            this.expr = conn.prepareExpression(xquery);
            this.expr.bindDocument(new javax.xml.namespace.QName("doc"), urlconn.getInputStream(), null, null);

            this.jaxbContext = JAXBContext.newInstance(XQueryHelper.Song.class);
            this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        }

        ArrayList<XQueryHelper.Song> getSongs() {
            ArrayList<XQueryHelper.Song> songs = new ArrayList<XQueryHelper.Song>();
            try {
                XQResultSequence rs = this.expr.executeQuery();
                while (rs.next()) {
                    XQItem item = rs.getItem();
                    XQueryHelper.Song song = (XQueryHelper.Song) jaxbUnmarshaller.unmarshal(item.getNode());
                    songs.add(song);
                }
            }
            catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
            finally { close(); }
            return songs;
        }

        private void close() {
            try {
                this.expr.close();
                this.conn.close();
            } catch (XQException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}

