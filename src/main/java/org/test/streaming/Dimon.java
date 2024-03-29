package org.test.streaming;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.oio.OioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.test.streaming.status.StatusHandler;

public class Dimon extends SimpleChannelUpstreamHandler {
	protected static final Log log = LogFactory.getLog(Dimon.class);

	private int port;
	private String ip;
	private Conf conf;

	public Dimon(int port) {
		this(null, port);
	}

	public Dimon(String ip, int port) {
		this.port = port;
		this.ip = ip;
		this.conf = new Conf();
	}

	public void run() {
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(new OioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// bootstrap.setOption("child.sendBufferSize", 1024);
		// bootstrap.setOption("sendBufferSize", 1024);

		// Set up the pipeline factory.
		final CachoServerHandler cachoServerHandler = new CachoServerHandler(conf);
		StatusHandler.getInstance().setCachoServerHandler(cachoServerHandler);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {

				return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(null)), new CachoServerHandler(conf));
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		// Bind and start to accept incoming connections.
		bootstrap.bind(this.ip != null ? new InetSocketAddress(this.ip, port) : new InetSocketAddress(port));
		log.info("Dimon is ready, awaiting for Cacho requests on port " + this.port + "...");
		log.info("Target upload bandwith " + conf.get("dimon.bytesps") + " bytes per second");
		log.info(this.conf.getCachosDir());
	}

	public void stop() {
		/*
		 * TODO
		 */
	}

	public static void main(String[] args) throws Exception {
		int port;
		String ip = null;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
			if (args.length > 1) {
				ip = args[1];
			}
		} else {
			port = 10002;
		}
		new Dimon(ip, port).run();
	}

}
