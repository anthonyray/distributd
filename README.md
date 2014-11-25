distributd
==========

Very simple implementation of Hadoop word count using SSH and NFS.

## Overview of the process on the Master Node

### Contact machines

The master will establish a list of available machines able to run SSH.

Here are the step of the contact algorithm :
- Nmap
- arp -a retrieves the list of the hosts
- check ssh connectivity
- remove hosts where ssh connectivity is impossible

The master keeps track of host in a ArrayList of Host data structure.


### File Splitting

According to the number of host, we split the file accordingly.
Fill a dictionnary that keeps track of splits.

### Split Mapping

A thread is created for every split.
This thread connect via SSH to a given host. Once connected, the host executes a jar on a split hosted in the distributed file system. The thread creates a data structure that allows to keep track of the **words for the given split.**

### Reducing

The master have the following data structures :
```
{
    word1 : [ split1, split2, split4],
    word2 : [ split2, split4],
    word3 : [split 1]
}
```

and
```
{
  split1 : machine1
  split2 : machine3
  split3 : machine2
}
```

For each word, we are going to create a thread. This thread will connect to every machine in the list of the word. By the end of the process, we will know the number of words for a given word.

The master have a synchronized data structure that will keep track of it.  
