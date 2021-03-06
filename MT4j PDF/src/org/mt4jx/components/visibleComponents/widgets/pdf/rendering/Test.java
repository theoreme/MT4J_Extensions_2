package org.mt4jx.components.visibleComponents.widgets.pdf.rendering;

import java.io.File;

import org.mt4jx.components.visibleComponents.widgets.pdf.RenderedPDFPage;

public class Test {
	public static void main(String[] args) {
		PDFRenderQueue queue = new PDFRenderQueue();
		JobExecutionThread thread = new JobExecutionThread(queue);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
		
		for (int i = 0; i < 20; i++) {
			PDFRenderJob job = new PDFRenderJob(new File("d:/Thesis%20Breier%20-%20Multitouch%203D.pdf"), i ,2.0d, i%3);
			queue.enque(job);
			job.addListener(new PDFRenderJobListener() {
				@Override
				public boolean stillRequired(PDFRenderJob job) {
					return true;
				}
				@Override
				public void jobFailed(PDFRenderJob job) {
					System.out.println("Failed.");
				}
				@Override
				public void jobDone(RenderedPDFPage pdf) {
					System.out.println("Done.");
				}
			});
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		queue.dump();
	}
}
