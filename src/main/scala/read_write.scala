package scala

import upickle.default._

private val path: os.Path = os.pwd/"src"/"main"/"scala"/"world_grid.json"

def gridToJSON(worldGrid: Array[Array[Square]]): Unit =
  val jsonStr: String = upickle.default.write(worldGrid)
  os.write(path, jsonStr)
end gridToJSON

def JSONToGrid(): Array[Array[Square]] =
  val worldGrid: Array[Array[Square]] = upickle.default.read[Array[Array[Square]]](os.read(path))
  worldGrid
end JSONToGrid
