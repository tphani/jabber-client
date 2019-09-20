package com.pht.jabber;

import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.net.ChannelEncryption;
import rocks.xmpp.core.net.client.SocketConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.core.stanza.model.Presence;

/**
 * https://bitbucket.org/sco0ter/babbler/src/master/
 * http://babbler-xmpp.blogspot.com/
 * https://dzone.com/articles/how-setup-custom
 */

public class App {
  public static void main(String[] args) throws Exception {
    TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
      @Override
      public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }

      @Override
      public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }

      @Override
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    } };
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, byPassTrustManagers, new SecureRandom());

    SocketConnectionConfiguration tcpConfiguration = SocketConnectionConfiguration.builder()
        .channelEncryption(ChannelEncryption.OPTIONAL)
        .sslContext(sslContext)
        .hostnameVerifier(new HostnameVerifier() {
          @Override
          public boolean verify(String arg0, SSLSession arg1) {
            return true;
          }
        })
        .hostname("cl7cup02a")
        .port(5222)
        .build();
    
    XmppClient client = XmppClient.create("corpimsvcs.com", tcpConfiguration);
    client.connect();
    client.login(MyConstants.user, MyConstants.pass);
//    client.sendPresence(new Presence(Presence.Show.DND));
    Message msg = new Message(Jid.of("snagabh2@corpimsvcs.com"), Message.Type.CHAT, "hello from babbler");
    client.sendMessage(msg);
    client.close();
  }
}
