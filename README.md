Introduction
============
OK, I accept it, I'm a lurker. 

And being up to date of pages and ideas in Twitter it's complicated. Because (as everybody out there) I don't always have the time to visit all interesting links, nor I can't copy every interesting idea I've just read...

So I started marking interesting twits as favorites, looking for free time to check them, but this manual task was boring, so I realize that I needed a program to retrieve, in a automatic way, all favorites I marked in Twitter. So this java application get born, using [twitter4j][0] and a bit of spare time.

This program retrieve all favorites of your Twitter acount, deleting as favorites and send them to you in an email (as storing them in a local file, just in case).

I'm adding new functionalities, such as retrieving information also from some Instapaper folders, and auto-sent emails and testing which retrieval options (timing, information combination, ...) fit best with my lifestyle...

I doubt that, like it is right now, fits to you, but maybe gives you an starting point to get your information in a more comfortable way.

(Classic) disclaimer: This program has been in "production" for a while, programmed in a daily cron, and it's been working properly for me, but I don't accept any responsability for any damage you suffer if decide to use it...


Application configuration
=========================
There is only one configuration file, containing necessary information to access to your Twitter account, and also information of the email account that'll receive email with all twits.

Due to obvious security reasons, I'm providing only a sample file, residing in *src/test/resources/twitter4j.properties_sample*.

Configuring twitter access
----------------------------------
First thing you have to do is to rename file twitter4j.properties_sample to twitter4j.properties and apply following steps (copied from <http://twitter4j.org/en/code-examples.html#oauth>):

First, you have to register the application in [Twitter][1], providing all necessary data (application name, description, ...). It's important to give write and read permissions.

Once we have consumer key y consumer secret, we'll write them down in twitter4j.properties file, and invoke *mvn assembly:assembly*, in order to get a .jar file in  target folder.

When executed that file with *java -cp target/FavoritesTwitter-0.1-SNAPSHOT-jar-with-dependencies.jar  org.okiju.favoritestwitter.OAuthSetup*, a dialog will start:

1. the program'll give us an URL, 
2. when used in a browser, this URL will show us a number, 
3. we'll give previous number to our program, receiving an accessToken in return, 
4. we'll store accessToken in twitter4j.properties file.

Finally, it's necessary to add an email address where to receive all emails.

Configuring email access
------------------------
I'm using my gmail account as an email account to send retrieved information through, and also as the email account where to extract personal emails from. 

In twitter4j.properties you have to provide origin and destination accounts (I have the same email address in both), as well as username and password information.

Configuring instapaper access
-----------------------------
In order to access to instapaper information, I'm using insta4j (https://github.com/dzontak/insta4j). As it's explained in that library page, you need to have a subscription, obtaining in back 2 oauth codes, that have to be stored in insta4j.properties (I provide a insta4j.properties_sample).

Configuring pinboard access
-----------------------------
In order to access to pinboard information, I'm using Jersey as REST client.  You have to provided username and password in twitter4j.properties as pinboard.username and pinboard.password

Invocation
==========
To invoke application you'll have to invoke with*java -jar target/FavoritesTwitter-0.2-SNAPSHOT-jar-with-dependencies.jar*.


[0]: http://twitter4j.org/
[1]: https://dev.twitter.com/apps/new
