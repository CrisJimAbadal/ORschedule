package XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import pojos.Surgery;




public class Java2XmlSurgery {
	
	//TODO more things of XML
	
	private static EntityManager em;
	private static BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
	

	
	public static void main(String[] args) throws Exception{
		
		em = Persistence.createEntityManagerFactory("DatabasesOR").createEntityManager(); 
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		
		// Create the JAXBContext
				JAXBContext jaxbContext = JAXBContext.newInstance(Surgery.class); 
				// Get the marshaller
				Marshaller marshaller = jaxbContext.createMarshaller();
				
				// Pretty formatting
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
				
				
				System.out.print("Choose a Surgery to turn into an XML file:");
				int Surg_id = Integer.parseInt(reader.readLine());
				Query q2 = em.createNativeQuery("SELECT * FROM Surgery WHERE id = ?", Surgery.class);
				q2.setParameter(1, Surg_id); 
				Surgery s = (Surgery) q2.getSingleResult();
				
				// Use the Marshaller to marshal the Java object to a file
				File file = new File("./xmls/ORschedule.xml"); //TODO create this file
				marshaller.marshal(s, file);
			
	}

}
