package edu.knowitall.tac2013.entitylinking

import KBPQuery.parseKBPQueries
import edu.knowitall.browser.entity.EntityLinker
import edu.knowitall.browser.entity.batch_match
import edu.knowitall.browser.entity.StringMatchCandidateFinder
import edu.knowitall.browser.entity.EntityTyper
import edu.knowitall.common.Resource.using
import edu.knowitall.tac2013.entitylinking.utils.WikiMappingHelper
import scopt.OptionParser

object RunKBPEntityLinkerSystem {
  
  var baseDir = "/scratch/resources/entitylinkingResources"
  val mapFile = baseDir + "/wikimap.txt"
  val wikiMap = using(io.Source.fromFile(mapFile, "UTF8")) { source =>
      WikiMappingHelper.loadNameToNodeIdMap(source.getLines)
    }
  
  private def linkQueries(queries: List[KBPQuery]): Unit = {
    
    val linkerSupportPath = new java.io.File("baseDir")
    val linker = new EntityLinker(
    		new batch_match(linkerSupportPath),
    		new StringMatchCandidateFinder(linkerSupportPath),
    		new EntityTyper(linkerSupportPath)
    		)
    
    
    for(q <- queries){
      val link = linker.getBestEntity(q.name, List(q.sourceContext))
      if(link == null){
        System.out.println("null")
      }
      else{
        val nodeId = wikiMap.get(link.entity.name)
        if(nodeId.isDefined){
          println(nodeId.get)
        }
        else{
          println("null")
        }
        
      }
    }
    
    
  }
  
  
  
  def main(args: Array[String]) {
 
    val argParser = new OptionParser() {
      arg("baseDir", "Path to base directory with entitylinking files.", { s => baseDir = s })
    }

    if(!argParser.parse(args)) return
    
    val queries = parseKBPQueries()
    linkQueries(queries)
    
    
  }
  

}