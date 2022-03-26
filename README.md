# Replication of a Distributed Denial of Service (DDoS) attack

Developed a project that replicates a synchronization of several clients to connect to a server at a predetermined time. This is a part of the requirements of a Distributed Denial of Service (DDoS) attack.

The parts invloved in this project are:

(a) a coordinator who communicates to nodes to connect ("attack")
(b) nodes who serve as clients to open a connection to the server and
(c) the server, who allows concurrent TCP connections on some designated port. 

The objective of the nodes is to open a TCP connection (each) to the server as close to simultaneously as possible.

Language: Java 
Working with TCP connections 
