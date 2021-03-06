package edu.knowitall.tac2013.entitylinking.coref
import edu.knowitall.tac2013.entitylinking.KBPQuery.parseKBPQueries
import java.io.PrintWriter
import java.io.FileWriter
import java.io.File
import edu.knowitall.tac2013.entitylinking.KBPQuery
import edu.knowitall.tac2013.entitylinking.SolrHelper

object SerializeCorefTypes {
    def main(args: Array[String]) {
	    val queries = parseKBPQueries(getClass.getResource("/edu/knowitall/tac2013/entitylinking/tac_2012_kbp_english_evaluation_entity_linking_queries.xml").getPath()).toSeq
	    val pw = new PrintWriter(new File("corefTypes.txt"))
	    pw.close()
	    for(q <- queries){
	       val fw = new FileWriter("corefTypes.txt",true)
	    	  fw.write(q.id)
	    	  val entityTypeList  = KBPQuery.corefHelper.getMatchingNamedEntities(SolrHelper.getRawDoc(q.doc),q.begOffset)
	    	  for(entityType <- scala.collection.JavaConversions.asScalaIterable(entityTypeList)){
	    	    fw.write("\t" +entityType)
	    	  }
	    	  fw.write("\n")
	    	fw.close()
	    }
    }
}