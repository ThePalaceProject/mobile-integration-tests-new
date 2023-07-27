package framework.utilities.feedXMLUtil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feed")
public class FeedModel {
    private List<EntryXML> entries;
    private List<LinkFromFeed> linksFromFeed;

    public List<EntryXML> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<EntryXML> entries) {
        this.entries = entries;
    }

    public List<LinkFromFeed> getLinksFromFeed() {
        return linksFromFeed;
    }

    @XmlElement(name = "link")
    public void setLinksFromFeed(List<LinkFromFeed> linksFromFeed) {
        this.linksFromFeed = linksFromFeed;
    }

}

class EntryXML {
    private String bookName;
    private String bookType;
    private String language;
    private Distribution distributor;
    private List<LinkFromEntry> linksFromEntry;

    public String getLanguage() {
        return language;
    }

    @XmlElement(name = "language", namespace = "http://purl.org/dc/terms/")
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookName() {
        return bookName;
    }

    @XmlElement(name = "title")
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    @XmlAttribute(name = "additionalType", namespace = "http://schema.org/")
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Distribution getDistributor() {
        return distributor;
    }

    @XmlElement(name = "distribution")
    public void setDistributor(Distribution distributor) {
        this.distributor = distributor;
    }

    public List<LinkFromEntry> getLinksFromEntry() {
        return linksFromEntry;
    }

    @XmlElement(name = "link")
    public void setLinksFromEntry(List<LinkFromEntry> linksFromEntry) {
        this.linksFromEntry = linksFromEntry;
    }
}

class Distribution {
    private String distributorName;

    public String getDistributorName() {
        return distributorName;
    }

    @XmlAttribute(name = "ProviderName", namespace = "http://bibframe.org/vocab/")
    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }
}

class LinkFromEntry {
    private Copies copies;
    private List<IndirectAcquisition> listOfIndirectAcquisition;
    private AvailabilityPDF availabilityPDF;

    public AvailabilityPDF getAvailabilityPDF() {
        return availabilityPDF;
    }

    @XmlElement(name = "availability", namespace = "http://opds-spec.org/2010/catalog")
    public void setAvailabilityPDF(AvailabilityPDF availabilityPDF) {
        this.availabilityPDF = availabilityPDF;
    }

    public Copies getCopies() {
        return copies;
    }

    @XmlElement(name = "copies")
    public void setCopies(Copies copies) {
        this.copies = copies;
    }

    public List<IndirectAcquisition> getListOfIndirectAcquisition() {
        return listOfIndirectAcquisition;
    }

    @XmlElement(name = "indirectAcquisition", namespace = "http://opds-spec.org/2010/catalog")
    public void setListOfIndirectAcquisition(List<IndirectAcquisition> listOfIndirectAcquisition) {
        this.listOfIndirectAcquisition = listOfIndirectAcquisition;
    }
}

class AvailabilityPDF{
    private String status;

    public String getStatus() {
        return status;
    }

    @XmlAttribute(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }
}

class LinkFromFeed {
    private String nextURLForXML;
    private String conditionForNextXML;

    public String getNextURLForXML() {
        return nextURLForXML;
    }

    @XmlAttribute(name = "href")
    public void setNextURLForXML(String nextURLForXML) {
        this.nextURLForXML = nextURLForXML;
    }

    public String getConditionForNextXML() {
        return conditionForNextXML;
    }

    @XmlAttribute(name = "rel")
    public void setConditionForNextXML(String conditionForNextXML) {
        this.conditionForNextXML = conditionForNextXML;
    }
}

class Copies {
    private int countAvailableCopies;

    public int getCountAvailableCopies() {
        return countAvailableCopies;
    }

    @XmlAttribute(name = "available")
    public void setCountAvailableCopies(int countAvailableCopies) {
        this.countAvailableCopies = countAvailableCopies;
    }
}

class IndirectAcquisition{
    private String type;
    private InternalIndirectAcquisition indirectAcquisition;

    public String getType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    public InternalIndirectAcquisition getInternalIndirectAcquisition() {
        return indirectAcquisition;
    }

    @XmlElement(name = "indirectAcquisition", namespace = "http://opds-spec.org/2010/catalog")
    public void setInternalIndirectAcquisition(InternalIndirectAcquisition indirectAcquisition) {
        this.indirectAcquisition = indirectAcquisition;
    }
}

class InternalIndirectAcquisition{
    private String type;

    public String getType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }
}
