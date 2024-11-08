Consumer.java - consumer class, receives messages from buffer for a given node
Producer.java - Producer class, for a given node, produces messages to send to neighbors
Node.java - Node class- implements features and functionality for each node in the network 
Message & MessageBuffer - Implementations for the messages nodes send to each other and the circular buffer that uses locks to be thread safe
VirtualNeworkSimulation.java - "Main" file, generates overlay for the network of nodes (each node and its neighbors), spawns threads and prints output for each node.