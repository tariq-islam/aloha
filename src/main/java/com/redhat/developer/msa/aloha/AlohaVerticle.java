/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.developer.msa.aloha;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class AlohaVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/").handler(ctx -> {
			String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");
			ctx.response()
					.putHeader("Content-Type", "text/plain")
					.putHeader("Access-Control-Allow-Origin", "*")
					.end(String.format("Aloha mai %s", hostname));
		});
		router.options("/").handler(ctx ->
				ctx.response()
						.putHeader("Access-Control-Allow-Origin", "*")
						.end());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}

}