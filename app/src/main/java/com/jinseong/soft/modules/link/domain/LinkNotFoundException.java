package com.jinseong.soft.modules.link.domain;

/**
 * 링크 not found 예외.
 */
public class LinkNotFoundException extends RuntimeException {
    public LinkNotFoundException(Long id) {
        super("Link not found - id: " + id);
    }
}
