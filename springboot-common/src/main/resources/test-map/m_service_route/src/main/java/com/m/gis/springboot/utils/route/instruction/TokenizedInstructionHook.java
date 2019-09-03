package com.m.gis.springboot.utils.route.instruction;

/**
 * A function to change the raw instruction string before tokens are replaced.
 * Useful to inject custom markup for tokens.
 */
public interface TokenizedInstructionHook {
  String change(String instruction);
}
